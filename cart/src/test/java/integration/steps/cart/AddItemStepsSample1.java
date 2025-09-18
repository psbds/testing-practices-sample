package integration.steps.cart;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.quarkus.logging.Log;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.Arrays;

import com.github.psbds.backends.stock.StockAPIClient;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductMetadataResponse;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.data.repository.dao.ItemRepositoryDAO;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;

import integration.lifecycle.Context;
import integration.mocks.backends.BackendMocks;
import integration.util.JwtTokenUtil;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;

public class AddItemStepsSample1 {
    @Inject
    BackendMocks backendAPIMocks;

    @Inject
    Context context;

    @Inject
    JwtTokenUtil jwtTokenUtil;

    @Inject
    ItemRepositoryDAO itemRepositoryDAO;

    private String stockProductId;
    private int stockQuantity;
    private BigDecimal stockPrice;
    private String stockMetadataName;
    private String stockMetadataValue;

    @Given("the product is valid and available in stock")
    public void theProductIsValidAndAvailableInStock() {
        // This step sets up a default product that is valid and has stock
        stockProductId = "1001";
        stockQuantity = 100;
        stockPrice = new BigDecimal("29.99");
        stockMetadataName = "size";
        stockMetadataValue = "M";
        // Create mock Stock API Client
        StockAPIClient stockAPIClientMock = backendAPIMocks.getStockAPIClientMock();

        // Create mock product response with good stock availability
        var mockMetadata = new StockAPIGetProductMetadataResponse();
        mockMetadata.setName(stockMetadataName);
        mockMetadata.setValues(Arrays.asList(stockMetadataValue));

        StockAPIGetProductResponse mockProduct = new StockAPIGetProductResponse();
        mockProduct.setName("Sample Product");
        mockProduct.setStock(stockQuantity);
        mockProduct.setPrice(stockPrice);
        mockProduct.setMetadata(Arrays.asList(mockMetadata));

        // Setup mock behavior for the default product
        when(stockAPIClientMock.getProduct(Long.parseLong(stockProductId))).thenReturn(mockProduct);
    }

    @When("I add a valid product to the cart")
    public void iAddAValidProductToTheCart() {
        // Use the product details set up in the @Given step
        var request = new ItemResourcePostItemRequest();
        request.setProductId(Long.parseLong(stockProductId));
        request.setQuantity(2); // Default quantity for testing
        request.setPrice(stockPrice);
        
        // Add metadata if it was set up
        ItemResourcePostItemMetadataRequest metadata = new ItemResourcePostItemMetadataRequest();
        metadata.setKey(stockMetadataName);
        metadata.setValue(stockMetadataValue);
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
    }

    
}
