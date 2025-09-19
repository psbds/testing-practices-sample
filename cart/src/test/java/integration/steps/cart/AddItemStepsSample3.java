package integration.steps.cart;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.psbds.backends.stock.StockAPIClient;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.data.repository.dao.ItemRepositoryDAO;
import com.github.psbds.data.repository.dao.ItemMetadataRepositoryDAO;
import com.github.psbds.domain.item.Item;
import com.github.psbds.domain.item.ItemMetadata;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;

import integration.lifecycle.Context;
import integration.mocks.backends.BackendMocks;
import integration.util.JwtTokenUtil;
import jakarta.inject.Inject;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AddItemStepsSample3 {
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

    @Given("the stock API will return the following product information for id {string}:")
    public void theStockAPIWillReturnTheFollowingProductInformation(String productId, String productJson)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StockAPIGetProductResponse product = objectMapper.readValue(productJson, StockAPIGetProductResponse.class);
        StockAPIClient stockAPIClientMock = backendAPIMocks.getStockAPIClientMock();
        when(stockAPIClientMock.getProduct(Long.parseLong(productId))).thenReturn(product);
    }

    @When("I send a POST request to the item endpoint with body:")
    public void iSendAPostRequestToTheItemEndpointWithBody(String requestBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Create the request object
        ItemResourcePostItemRequest request = objectMapper.readValue(requestBody, ItemResourcePostItemRequest.class);

        // Generate JWT token for the current user
        String userId = context.getCurrentUserId();
        String jwtToken = jwtTokenUtil.generateUserToken(userId);

        // Send the request
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .body(request)
                .post("/item");

        context.setLastResponse(response);
    }

    @Then("the following items should be present in the Items table in the database:")
    public void theFollowingItemsShouldBePresentInTheItemsTableInTheDatabase(DataTable dataTable) {
        // Convert DataTable to list of maps for easier processing
        List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> expectedItem : expectedItems) {
            String userId = expectedItem.get("user_id");
            Long productId = Long.parseLong(expectedItem.get("product_id"));
            int expectedQuantity = Integer.parseInt(expectedItem.get("quantity"));
            BigDecimal expectedPrice = new BigDecimal(expectedItem.get("price"));

            // Query the database for items matching userId and productId
            List<Item> actualItems = itemRepositoryDAO
                    .find("userId = ?1 and productId = ?2 and quantity = ?3 and price = ?4", userId, productId,
                            expectedQuantity, expectedPrice)
                    .list();

            // Verify that at least one item exists
            assertFalse(actualItems.isEmpty(),
                    String.format(
                            "No item found in database for user_id=%s and product_id=%s and quantity=%d and price=%s",
                            userId, productId, expectedQuantity, expectedPrice));
        }
    }

    @Then("the following metadata should be present in the ItemMetadata table in the database:")
    public void theFollowingMetadataShouldBePresentInTheItemMetadataTableInTheDatabase(DataTable dataTable) {
        // Convert DataTable to list of maps for easier processing
        List<Map<String, String>> expectedMetadata = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> expectedMetadataEntry : expectedMetadata) {
            String key = expectedMetadataEntry.get("[key]");
            String value = expectedMetadataEntry.get("[value]");

            // Query the database for metadata matching key and value
            List<ItemMetadata> actualMetadata = itemMetadataRepositoryDAO.find("key = ?1 and value = ?2", key, value)
                    .list();

            // Verify that at least one metadata entry exists
            assertFalse(actualMetadata.isEmpty(),
                    String.format("No metadata found in database for key=%s and value=%s", key, value));
        }
    }

    @Then("the response should contain the same item id inserted in the database")
    public void theResponseShouldContainTheItemId() {
        Response response = context.getLastResponse();

        // Extract the response body and verify it contains an ID
        ItemResourcePostItemResponse responseBody = response.as(ItemResourcePostItemResponse.class);

        var items = itemRepositoryDAO.listAll();
        var singleItem = items.get(items.size() - 1); 
        assertEquals(singleItem.getId(), responseBody.getId(), "Response item ID should match the created item ID");
    }
}