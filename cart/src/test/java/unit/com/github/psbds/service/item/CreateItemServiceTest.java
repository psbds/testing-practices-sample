package unit.com.github.psbds.service.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.data.repository.ItemMetadataRepository;
import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.domain.item.Item;
import com.github.psbds.domain.item.ItemMetadata;
import com.github.psbds.factory.item.ItemFactory;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.service.item.CreateItemService;
import com.github.psbds.service.product.ValidateCartProductService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@QuarkusComponentTest
class CreateItemServiceTest {

    private static final String TEST_USER_ID = "test-user-123";
    private static final Long TEST_ITEM_ID = 12345L;
    private static final Long TEST_PRODUCT_ID = 1001L;
    private static final Integer TEST_QUANTITY = 2;
    private static final BigDecimal TEST_PRICE = new BigDecimal("29.99");

    @Inject
    CreateItemService createItemService;

    @InjectMock
    ValidateCartProductService validateCartProductService;

    @InjectMock
    ItemFactory itemFactory;

    @InjectMock
    ItemRepository itemRepository;

    @InjectMock
    ItemMetadataRepository itemMetadataRepository;

    @Test
    void create_when_validRequestProvided_should_validateProductCreateItemPersistItemAndMetadataAndReturnResponse() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequest();
        Item mockItem = mock(Item.class);
        ItemMetadata mockMetadata1 = mock(ItemMetadata.class);
        ItemMetadata mockMetadata2 = mock(ItemMetadata.class);
        List<ItemMetadata> metadata = Arrays.asList(mockMetadata1, mockMetadata2);

        when(itemFactory.create(TEST_USER_ID, mockRequest)).thenReturn(mockItem);
        when(mockItem.getId()).thenReturn(TEST_ITEM_ID);
        when(mockItem.getMetadata()).thenReturn(metadata);

        // Act
        ItemResourcePostItemResponse result = createItemService.create(TEST_USER_ID, mockRequest);

        // Assert
        assertNotNull(result, "Response should not be null");
        assertEquals(TEST_ITEM_ID, result.getId(), "Response should contain the expected item ID");

        // Verify validation is called with converted metadata
        verify(validateCartProductService).validateCartProduct(
                eq(TEST_PRODUCT_ID),
                eq(TEST_QUANTITY),
                eq(TEST_PRICE),
                argThat(metadataMap -> metadataMap.containsKey("color") &&
                        metadataMap.get("color").equals("red") &&
                        metadataMap.containsKey("size") &&
                        metadataMap.get("size").equals("large")));

        // Verify the service orchestrates the operations in correct order
        verify(itemFactory).create(TEST_USER_ID, mockRequest);
        verify(itemRepository).persist(mockItem);
        verify(itemMetadataRepository).persist(metadata);
    }

    @Test
    void create_when_itemWithEmptyMetadata_should_validateWithEmptyMapAndPersistEmptyMetadataList() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = createMockRequestWithEmptyMetadata();
        Item mockItem = mock(Item.class);
        List<ItemMetadata> emptyMetadata = Arrays.asList();

        when(itemFactory.create(TEST_USER_ID, mockRequest)).thenReturn(mockItem);
        when(mockItem.getId()).thenReturn(TEST_ITEM_ID);
        when(mockItem.getMetadata()).thenReturn(emptyMetadata);

        // Act
        ItemResourcePostItemResponse result = createItemService.create(TEST_USER_ID, mockRequest);

        // Assert
        assertNotNull(result, "Response should not be null");
        assertEquals(TEST_ITEM_ID, result.getId(), "Response should contain the expected item ID");

        // Verify validation is called with empty metadata map
        verify(validateCartProductService).validateCartProduct(
                eq(TEST_PRODUCT_ID),
                eq(TEST_QUANTITY),
                eq(TEST_PRICE),
                argThat(metadataMap -> metadataMap.isEmpty()));

        // Verify empty metadata list is persisted
        verify(itemFactory).create(TEST_USER_ID, mockRequest);
        verify(itemRepository).persist(mockItem);
        verify(itemMetadataRepository).persist(emptyMetadata);
    }

    private ItemResourcePostItemRequest createMockRequest() {
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
        ItemResourcePostItemMetadataRequest mockMetadata1 = mock(ItemResourcePostItemMetadataRequest.class);
        ItemResourcePostItemMetadataRequest mockMetadata2 = mock(ItemResourcePostItemMetadataRequest.class);

        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);

        when(mockMetadata1.getKey()).thenReturn("color");
        when(mockMetadata1.getValue()).thenReturn("red");
        when(mockMetadata2.getKey()).thenReturn("size");
        when(mockMetadata2.getValue()).thenReturn("large");

        when(mockRequest.getMetadata()).thenReturn(Arrays.asList(mockMetadata1, mockMetadata2));

        return mockRequest;
    }

    private ItemResourcePostItemRequest createMockRequestWithEmptyMetadata() {
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);

        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);
        when(mockRequest.getMetadata()).thenReturn(Arrays.asList());

        return mockRequest;
    }

    @Test
    void testServiceHasApplicationScopedAnnotation() {
        // Arrange
        Class<CreateItemService> serviceClass = CreateItemService.class;

        // Act
        ApplicationScoped annotation = serviceClass.getAnnotation(ApplicationScoped.class);

        // Assert
        assertNotNull(annotation,
                "CreateItemService should have @ApplicationScoped annotation");
    }
}