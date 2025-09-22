package integration.steps.cart;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.quarkus.logging.Log;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.github.psbds.backends.stock.StockAPIClient;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductMetadataResponse;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.data.repository.dao.ItemRepositoryDAO;
import com.github.psbds.data.repository.dao.ItemMetadataRepositoryDAO;

import integration.mocks.backends.BackendMocks;
import integration.lifecycle.Context;
import integration.util.JwtTokenUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetItemsSteps {

    @Inject
    BackendMocks backendAPIMocks;

    @Inject
    Context context;

    @Inject
    JwtTokenUtil jwtTokenUtil;

    @Inject
    ItemRepositoryDAO itemRepositoryDAO;

    @Inject
    ItemMetadataRepositoryDAO itemMetadataRepositoryDAO;

    @Given("I have the following item in my cart:")
    public void iHaveTheFollowingItemInMyCart(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        String userId = context.getCurrentUserId();
        
        for (Map<String, String> itemData : items) {
            addItemToCart(userId, itemData);
        }
    }

    @Given("I have the following items in my cart:")
    public void iHaveTheFollowingItemsInMyCart(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        String userId = context.getCurrentUserId();
        
        for (Map<String, String> itemData : items) {
            addItemToCart(userId, itemData);
        }
    }

    @Given("another user {string} has the following item in their cart:")
    public void anotherUserHasTheFollowingItemInTheirCart(String otherUserId, DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> itemData : items) {
            addItemToCart(otherUserId, itemData);
        }
    }

    @When("I get all items from my cart")
    public void iGetAllItemsFromMyCart() {
        String userId = context.getCurrentUserId();
        String jwtToken = jwtTokenUtil.generateUserToken(userId);

        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .get("/item");

        context.setLastResponse(response);
        Log.info("Retrieved items for user " + userId + " with response status: " + response.getStatusCode());
    }

    @When("I try to get all items from my cart without authentication")
    public void iTryToGetAllItemsFromMyCartWithoutAuthentication() {
        Response response = RestAssured.given()
                .contentType("application/json")
                .get("/item");

        context.setLastResponse(response);
        Log.info("Attempted to get items without authentication with response status: " + response.getStatusCode());
    }

    @Then("the response should contain an empty list of items")
    public void theResponseShouldContainAnEmptyListOfItems() {
        Response response = context.getLastResponse();
        ItemResourceGetItemsResponse responseBody = response.as(ItemResourceGetItemsResponse.class);

        assertNotNull(responseBody, "Response body should not be null");
        assertNotNull(responseBody.getItems(), "Items list should not be null");
        assertEquals(0, responseBody.getItems().size(), "Items list should be empty");

        Log.info("Verified that response contains an empty list of items");
    }

    @Then("the response should contain {int} item")
    public void theResponseShouldContainItem(int expectedCount) {
        Response response = context.getLastResponse();
        ItemResourceGetItemsResponse responseBody = response.as(ItemResourceGetItemsResponse.class);
        Log.info("Verified that response contains " + expectedCount + " item(s)");

        assertNotNull(responseBody, "Response body should not be null");
        assertNotNull(responseBody.getItems(), "Items list should not be null");
        assertEquals(expectedCount, responseBody.getItems().size(), 
            "Items list should contain " + expectedCount + " item(s)");

        Log.info("Verified that response contains " + expectedCount + " item(s)");
    }

    @Then("the response should contain {int} items")
    public void theResponseShouldContainItems(int expectedCount) {
        theResponseShouldContainItem(expectedCount);
    }

    @Then("the response should contain an item with product_id {string}, quantity {string}, and price {string}")
    public void theResponseShouldContainAnItemWithProductIdQuantityAndPrice(String productId, String quantity, String price) {
        Response response = context.getLastResponse();
        ItemResourceGetItemsResponse responseBody = response.as(ItemResourceGetItemsResponse.class);

        assertNotNull(responseBody, "Response body should not be null");
        assertNotNull(responseBody.getItems(), "Items list should not be null");

        Long expectedProductId = Long.parseLong(productId);
        Integer expectedQuantity = Integer.parseInt(quantity);
        BigDecimal expectedPrice = new BigDecimal(price);

        boolean itemFound = responseBody.getItems().stream()
                .anyMatch(item -> 
                    expectedProductId.equals(item.getProductId()) &&
                    expectedQuantity.equals(item.getQuantity()) &&
                    expectedPrice.compareTo(item.getPrice()) == 0);

        assertTrue(itemFound, 
            "Response should contain an item with product_id=" + productId + 
            ", quantity=" + quantity + ", price=" + price);

        Log.info("Verified that response contains item with product_id=" + productId + 
                 ", quantity=" + quantity + ", price=" + price);
    }

    @Then("the response should not contain an item with product_id {string}")
    public void theResponseShouldNotContainAnItemWithProductId(String productId) {
        Response response = context.getLastResponse();
        ItemResourceGetItemsResponse responseBody = response.as(ItemResourceGetItemsResponse.class);

        assertNotNull(responseBody, "Response body should not be null");
        assertNotNull(responseBody.getItems(), "Items list should not be null");

        Long expectedProductId = Long.parseLong(productId);

        boolean itemFound = responseBody.getItems().stream()
                .anyMatch(item -> expectedProductId.equals(item.getProductId()));

        assertFalse(itemFound, 
            "Response should NOT contain an item with product_id=" + productId);

        Log.info("Verified that response does not contain item with product_id=" + productId);
    }

    @Then("the item should contain metadata with key {string} and value {string}")
    public void theItemShouldContainMetadataWithKeyAndValue(String key, String value) {
        Response response = context.getLastResponse();
        ItemResourceGetItemsResponse responseBody = response.as(ItemResourceGetItemsResponse.class);

        assertNotNull(responseBody, "Response body should not be null");
        assertNotNull(responseBody.getItems(), "Items list should not be null");
        assertFalse(responseBody.getItems().isEmpty(), "Items list should not be empty");

        // Assuming we're checking the first item (for single item scenarios)
        ItemResourceGetItemsItemResponse item = responseBody.getItems().get(0);
        assertNotNull(item.getMetadata(), "Item metadata should not be null");

        boolean metadataFound = item.getMetadata().stream()
                .anyMatch(metadata -> 
                    key.equals(metadata.getKey()) && value.equals(metadata.getValue()));

        assertTrue(metadataFound, 
            "Item should contain metadata with key='" + key + "' and value='" + value + "'");

        Log.info("Verified that item contains metadata with key='" + key + "' and value='" + value + "'");
    }

    // Private helper method to add items to cart
    private void addItemToCart(String userId, Map<String, String> itemData) {
        String productId = itemData.get("product_id");
        String quantity = itemData.get("quantity");
        String price = itemData.get("price");
        String metadataKey = itemData.get("metadata_key");
        String metadataValue = itemData.get("metadata_value");

        // Mock the stock API for this product
        mockStockApiForProduct(productId, quantity, price, metadataKey, metadataValue);

        // Create the add item request
        var request = new ItemResourcePostItemRequest();
        request.setProductId(Long.parseLong(productId));
        request.setQuantity(Integer.parseInt(quantity));
        request.setPrice(new BigDecimal(price));

        // Add metadata if provided
        if (metadataKey != null && metadataValue != null) {
            ItemResourcePostItemMetadataRequest metadata = new ItemResourcePostItemMetadataRequest();
            metadata.setKey(metadataKey);
            metadata.setValue(metadataValue);
            request.setMetadata(Arrays.asList(metadata));
        }

        // Generate JWT token for the user
        String jwtToken = jwtTokenUtil.generateUserToken(userId);

        // Add the item to cart via REST API
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .body(request)
                .post("/item");

        // Verify the item was added successfully
        assertEquals(200, response.getStatusCode(), 
            "Failed to add item to cart for user " + userId);

        Log.info("Added item to cart for user " + userId + ": product_id=" + productId + 
                 ", quantity=" + quantity + ", price=" + price);
    }

    // Private helper method to mock stock API
    private void mockStockApiForProduct(String productId, String quantity, String price, 
                                       String metadataKey, String metadataValue) {
        StockAPIClient stockAPIClientMock = backendAPIMocks.getStockAPIClientMock();

        // Create mock metadata if provided
        StockAPIGetProductMetadataResponse mockMetadata = null;
        if (metadataKey != null && metadataValue != null) {
            mockMetadata = new StockAPIGetProductMetadataResponse();
            mockMetadata.setName(metadataKey);
            mockMetadata.setValues(Arrays.asList(metadataValue));
        }

        // Create mock product response
        StockAPIGetProductResponse mockProduct = new StockAPIGetProductResponse();
        mockProduct.setName("Test Product " + productId);
        mockProduct.setStock(Integer.parseInt(quantity) + 10); // Ensure sufficient stock
        mockProduct.setPrice(new BigDecimal(price));
        
        if (mockMetadata != null) {
            mockProduct.setMetadata(Arrays.asList(mockMetadata));
        }

        // Setup mock behavior
        when(stockAPIClientMock.getProduct(Long.parseLong(productId))).thenReturn(mockProduct);
    }
}