package integration.steps.cart;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.quarkus.logging.Log;
import io.restassured.RestAssured;

import com.github.psbds.backends.stock.StockAPIClient;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;

import integration.mocks.backends.BackendMocks;
import integration.lifecycle.Context;
import integration.util.JwtTokenUtil;
import java.math.BigDecimal;
import java.util.Arrays;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;

public class AddItemSteps {

    @Inject
    BackendMocks backendAPIMocks;
    
    @Inject
    Context context;
    
    @Inject
    JwtTokenUtil jwtTokenUtil;

    @Given("the product id {string} exists in the product catalog with {string} items in stock and price {string}")
    public void theProductIdExistsInTheProductCatalogWithItemsInStock(String productId, String stockQuantity, String price) {
        // Create mock Stock API Client
        StockAPIClient stockAPIClientMock = backendAPIMocks.getStockAPIClientMock();

        // Create mock product response
        StockAPIGetProductResponse mockProduct = new StockAPIGetProductResponse();
        mockProduct.setName("Sample Product");
        mockProduct.setStock(Integer.parseInt(stockQuantity));
        mockProduct.setPrice(new BigDecimal(price));
        mockProduct.setMetadata(Arrays.asList());

        // Setup mock behavior
        when(stockAPIClientMock.getProduct(Long.parseLong(productId))).thenReturn(mockProduct);
    }

    @When("I add product {string} to cart with quantity {string} and price {string}")
    public void iAddProductToCartWithQuantityAndPrice(String productId, String quantity, String price) {
        var request = new ItemResourcePostItemRequest();
        request.setProductId(Long.parseLong(productId));
        request.setQuantity(Integer.parseInt(quantity));
        request.setPrice(new BigDecimal(price));
        request.setMetadata(Arrays.asList());
        
        // Generate JWT token for the current user
        String userId = context.getCurrentUserId();
        Log.error("UserId " + userId);
        
        String jwtToken = jwtTokenUtil.generateUserToken(userId);
        Log.error("Generated JWT token for user: " + userId);
        
        var response = RestAssured.given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + jwtToken)
            .body(request)
            .post("/item");

        Log.error("Response from POST /item: " + response.statusCode());
        Log.error("Response from POST /item: " + response.asString());
    }
}
