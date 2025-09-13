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
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.github.psbds.service.item.CreateItemService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.List;

@QuarkusComponentTest
class CreateItemServiceTest {

    private static final String TEST_USER_ID = "test-user-123";
    private static final Long TEST_ITEM_ID = 12345L;

    @Inject
    CreateItemService createItemService;

    @InjectMock
    ItemFactory itemFactory;

    @InjectMock
    ItemRepository itemRepository;

    @InjectMock
    ItemMetadataRepository itemMetadataRepository;

    @Test
    void create_when_validRequestProvided_should_createItemPersistItemAndMetadataAndReturnResponse() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
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

        // Verify the service orchestrates the operations in correct order
        verify(itemFactory).create(TEST_USER_ID, mockRequest);
        verify(itemRepository).persist(mockItem);
        verify(itemMetadataRepository).persist(metadata);
    }

    @Test
    void create_when_itemWithEmptyMetadata_should_persistEmptyMetadataList() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
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

        // Verify empty metadata list is persisted
        verify(itemFactory).create(TEST_USER_ID, mockRequest);
        verify(itemRepository).persist(mockItem);
        verify(itemMetadataRepository).persist(emptyMetadata);
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