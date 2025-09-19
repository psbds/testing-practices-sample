package integration.steps.cart;

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
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;
import com.github.psbds.data.repository.dao.ItemRepositoryDAO;
import com.github.psbds.domain.item.Item;

import integration.mocks.backends.BackendMocks;
import integration.lifecycle.Context;
import integration.util.JwtTokenUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddItemStepsSample2 {

    @Inject
    BackendMocks backendAPIMocks;

    @Inject
    Context context;

    @Inject
    JwtTokenUtil jwtTokenUtil;

    @Inject
    ItemRepositoryDAO itemRepositoryDAO;

    @Given("the product id {string} exists in the product catalog with {string} items in stock and price {string} and allows the metadata {string} with values {string}")
    public void theProductIdExistsInTheProductCatalogWithMetadata(String productId, String stockQuantity, String price,
            String metadataKey, String metadataValues) {
        // Create mock Stock API Client
        StockAPIClient stockAPIClientMock = backendAPIMocks.getStockAPIClientMock();

        // Parse metadata values (e.g., "S, M, L" or "'S', 'M', 'L'" -> ["S", "M", "L"])
        String[] allowedValues = metadataValues.replaceAll("'", "").split(",\\s*");

        // Create mock product response
        StockAPIGetProductMetadataResponse metadata = new StockAPIGetProductMetadataResponse();
        metadata.setName(metadataKey);
        metadata.setValues(Arrays.asList(allowedValues));
        StockAPIGetProductResponse mockProduct = new StockAPIGetProductResponse();
        mockProduct.setName("Sample Product");
        mockProduct.setStock(Integer.parseInt(stockQuantity));
        mockProduct.setPrice(new BigDecimal(price));
        mockProduct.setMetadata(Arrays.asList(metadata));

        // Setup mock behavior
        when(stockAPIClientMock.getProduct(Long.parseLong(productId))).thenReturn(mockProduct);
    }

    @When("I add product {string} to cart with quantity {string} and price {string} and metadata {string} with value {string}")
    public void iAddProductToCartWithQuantityAndPriceAndMetadata(String productId, String quantity, String price,
            String metadataKey, String metadataValue) {
        var request = new ItemResourcePostItemRequest();
        request.setProductId(Long.parseLong(productId));
        request.setQuantity(Integer.parseInt(quantity));
        request.setPrice(new BigDecimal(price));

        // Create metadata with the provided key-value pair
        ItemResourcePostItemMetadataRequest metadata = new ItemResourcePostItemMetadataRequest();
        metadata.setKey(metadataKey);
        metadata.setValue(metadataValue);
        request.setMetadata(Arrays.asList(metadata));

        // Generate JWT token for the current user
        String userId = context.getCurrentUserId();
        String jwtToken = jwtTokenUtil.generateUserToken(userId);

        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .body(request)
                .post("/item");

        context.setLastResponse(response);

        Log.info("Added product " + productId + " to cart with quantity=" + quantity +
                ", price=" + price + ", metadata=" + metadataKey + ":" + metadataValue +
                " for user " + userId);
    }

    @Then("the cart should contain product {string} with quantity {string} and price {string}")
    public void theCartShouldContainProductWithQuantityAndPrice(String productId, String expectedQuantity,
            String expectedPrice) {
        // Get the current user ID to query their items
        String userId = context.getCurrentUserId();

        // Query the database directly using ItemRepositoryDAO
        var items = itemRepositoryDAO.find("userId = ?1 and productId = ?2", userId, Long.parseLong(productId)).list();

        // Validate that the item was inserted
        assertFalse(items.isEmpty(),
                "Item should exist in the database for user " + userId + " and product " + productId);

        // Get the first matching item (there should be only one)
        Item item = items.get(0);

        // Validate the item properties
        assertEquals(Long.parseLong(productId), item.getProductId(), "Product ID should match");
        assertEquals(Integer.parseInt(expectedQuantity), item.getQuantity(), "Quantity should match");
        assertEquals(new BigDecimal(expectedPrice), item.getPrice(), "Price should match");
        assertEquals(userId, item.getUserId(), "User ID should match");
    }

    @Then("the response should contain the item id inserted in the database")
    public void theResponseShouldContainTheItemId() {
        Response response = context.getLastResponse();

        // Extract the response body and verify it contains an ID
        ItemResourcePostItemResponse responseBody = response.as(ItemResourcePostItemResponse.class);

        var items = itemRepositoryDAO.listAll();
        var singleItem = items.get(items.size() - 1); 
        assertEquals(singleItem.getId(), responseBody.getId(), "Response item ID should match the created item ID");
    }

    @Then("the cart should not contain product {string}")
    public void theCartShouldNotContainProduct(String productId) {
        // Get the current user ID to query their items
        String userId = context.getCurrentUserId();

        // Query the database directly using ItemRepositoryDAO to check if product exists
        var items = itemRepositoryDAO.find("userId = ?1 and productId = ?2", userId, Long.parseLong(productId)).list();

        // Validate that the item does NOT exist in the database
        assertTrue(items.isEmpty(),
                "Product " + productId + " should NOT exist in the cart for user " + userId + 
                ", but found " + items.size() + " item(s)");

        Log.info("Successfully verified that product " + productId + " is NOT in the cart for user " + userId);
    }
}
