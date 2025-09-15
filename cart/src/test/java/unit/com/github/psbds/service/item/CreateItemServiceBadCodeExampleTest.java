package unit.com.github.psbds.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.psbds.backends.stock.StockAPIClientWrapper;
import com.github.psbds.service.item.CreateItemServiceBadCodeExample;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductMetadataResponse;
import com.github.psbds.backends.stock.model.getproduct.StockAPIGetProductResponse;
import com.github.psbds.data.repository.ItemMetadataRepository;
import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.domain.item.Item;
import com.github.psbds.errors.ErrorCodes;
import com.github.psbds.errors.exception.BusinessException;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;

import jakarta.enterprise.context.ApplicationScoped;

class CreateItemServiceBadCodeExampleTest {

    private static final String TEST_USER_ID = "user123";
    private static final Long TEST_PRODUCT_ID = 101L;
    private static final Integer TEST_QUANTITY = 5;
    private static final BigDecimal TEST_PRICE = new BigDecimal("99.99");
    private static final Integer TEST_STOCK = 10;
    private static final Long TEST_ITEM_ID = 1L;

    private CreateItemServiceBadCodeExample createItemService;
    private ItemRepository mockItemRepository;
    private ItemMetadataRepository mockItemMetadataRepository;
    private StockAPIClientWrapper mockStockApiClient;

    @BeforeEach
    void setUp() {
        mockItemRepository = mock(ItemRepository.class);
        mockItemMetadataRepository = mock(ItemMetadataRepository.class);
        mockStockApiClient = mock(StockAPIClientWrapper.class);

        createItemService = new CreateItemServiceBadCodeExample(mockItemRepository, mockItemMetadataRepository, mockStockApiClient);
    }

    @Test
    void create_when_allValidationsPass_should_createItemAndPersistSuccessfully() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequest();
        StockAPIGetProductResponse mockProduct = createMockProduct();
        Item mockItem = mock(Item.class);
        when(mockItem.getId()).thenReturn(TEST_ITEM_ID);

        when(mockStockApiClient.getProduct(TEST_PRODUCT_ID)).thenReturn(mockProduct);

        // Act
        ItemResourcePostItemResponse result = createItemService.create(TEST_USER_ID, mockRequest);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertNull(result.getId(), "Response should be null");

        verify(mockStockApiClient).getProduct(TEST_PRODUCT_ID);
        verify(mockItemRepository).persist(any(Item.class));
        verify(mockItemMetadataRepository).persist(anyList());
    }

    @Test
    void create_when_stockIsInsufficient_should_throwBusinessException() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequest();
        StockAPIGetProductResponse mockProduct = createMockProduct();
        when(mockProduct.getStock()).thenReturn(3); // Less than requested quantity (5)

        when(mockStockApiClient.getProduct(TEST_PRODUCT_ID)).thenReturn(mockProduct);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> createItemService.create(TEST_USER_ID, mockRequest), "Should throw BusinessException when stock is insufficient");

        assertEquals(ErrorCodes.INSUFFICIENT_STOCK, exception.getErrorCode(), "Should have correct error code");
        assertEquals("Insufficient items in Stock", exception.getErrorMessage(), "Should have correct error message");
    }

    @Test
    void create_when_priceDoesNotMatch_should_throwBusinessException() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequest();
        StockAPIGetProductResponse mockProduct = createMockProduct();
        when(mockProduct.getPrice()).thenReturn(new BigDecimal("89.99")); // Different from request price

        when(mockStockApiClient.getProduct(TEST_PRODUCT_ID)).thenReturn(mockProduct);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> createItemService.create(TEST_USER_ID, mockRequest), "Should throw BusinessException when price does not match");

        assertEquals(ErrorCodes.INVALID_PRICE, exception.getErrorCode(), "Should have correct error code");
        assertEquals("Product price does not match current price", exception.getErrorMessage(), "Should have correct error message");
    }

    @Test
    void create_when_metadataKeyDoesNotExist_should_throwBusinessException() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequestWithInvalidMetadataKey();
        StockAPIGetProductResponse mockProduct = createMockProduct();

        when(mockStockApiClient.getProduct(TEST_PRODUCT_ID)).thenReturn(mockProduct);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> createItemService.create(TEST_USER_ID, mockRequest),
                "Should throw BusinessException when metadata key does not exist");

        assertEquals(ErrorCodes.INVALID_ITEM_METADATA, exception.getErrorCode(), "Should have correct error code");
        assertEquals("Invalid item metadata", exception.getErrorMessage(), "Should have correct error message");
        assertNotNull(exception.getErrors(), "Should have error details");
    }

    @Test
    void create_when_metadataValueIsInvalid_should_throwBusinessException() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequestWithInvalidMetadataValue();
        StockAPIGetProductResponse mockProduct = createMockProduct();

        when(mockStockApiClient.getProduct(TEST_PRODUCT_ID)).thenReturn(mockProduct);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> createItemService.create(TEST_USER_ID, mockRequest), "Should throw BusinessException when metadata value is invalid");

        assertEquals(ErrorCodes.INVALID_ITEM_METADATA, exception.getErrorCode(), "Should have correct error code");
        assertEquals("Invalid item metadata", exception.getErrorMessage(), "Should have correct error message");
        assertNotNull(exception.getErrors(), "Should have error details");
    }

    @Test
    void testServiceHasApplicationScopedAnnotation() {
        // Arrange
        Class<CreateItemServiceBadCodeExample> serviceClass = CreateItemServiceBadCodeExample.class;

        // Act
        ApplicationScoped annotation = serviceClass.getAnnotation(ApplicationScoped.class);

        // Assert
        assertNotNull(annotation, "CreateItemServiceBadCodeExample should have @ApplicationScoped annotation");
    }

    private ItemResourcePostItemRequest createMockRequest() {
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
        ItemResourcePostItemMetadataRequest mockMetadata1 = mock(ItemResourcePostItemMetadataRequest.class);
        ItemResourcePostItemMetadataRequest mockMetadata2 = mock(ItemResourcePostItemMetadataRequest.class);

        when(mockMetadata1.getKey()).thenReturn("color");
        when(mockMetadata1.getValue()).thenReturn("red");
        when(mockMetadata2.getKey()).thenReturn("size");
        when(mockMetadata2.getValue()).thenReturn("large");

        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);
        when(mockRequest.getMetadata()).thenReturn(Arrays.asList(mockMetadata1, mockMetadata2));

        return mockRequest;
    }

    private ItemResourcePostItemRequest createMockRequestWithInvalidMetadataKey() {
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
        ItemResourcePostItemMetadataRequest mockMetadata = mock(ItemResourcePostItemMetadataRequest.class);

        when(mockMetadata.getKey()).thenReturn("invalidKey");
        when(mockMetadata.getValue()).thenReturn("someValue");

        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);
        when(mockRequest.getMetadata()).thenReturn(Arrays.asList(mockMetadata));

        return mockRequest;
    }

    private ItemResourcePostItemRequest createMockRequestWithInvalidMetadataValue() {
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
        ItemResourcePostItemMetadataRequest mockMetadata = mock(ItemResourcePostItemMetadataRequest.class);

        when(mockMetadata.getKey()).thenReturn("color");
        when(mockMetadata.getValue()).thenReturn("purple"); // Invalid value for color

        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);
        when(mockRequest.getMetadata()).thenReturn(Arrays.asList(mockMetadata));

        return mockRequest;
    }

    private StockAPIGetProductResponse createMockProduct() {
        StockAPIGetProductResponse mockProduct = mock(StockAPIGetProductResponse.class);
        StockAPIGetProductMetadataResponse mockColorMetadata = mock(StockAPIGetProductMetadataResponse.class);
        StockAPIGetProductMetadataResponse mockSizeMetadata = mock(StockAPIGetProductMetadataResponse.class);

        when(mockColorMetadata.getName()).thenReturn("color");
        when(mockColorMetadata.getValues()).thenReturn(Arrays.asList("red", "blue", "green"));

        when(mockSizeMetadata.getName()).thenReturn("size");
        when(mockSizeMetadata.getValues()).thenReturn(Arrays.asList("small", "medium", "large"));

        when(mockProduct.getStock()).thenReturn(TEST_STOCK);
        when(mockProduct.getPrice()).thenReturn(TEST_PRICE);
        when(mockProduct.getMetadata()).thenReturn(Arrays.asList(mockColorMetadata, mockSizeMetadata));

        return mockProduct;
    }
}