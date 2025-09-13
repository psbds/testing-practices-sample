package unit.com.github.psbds.service.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.domain.item.Item;
import com.github.psbds.mapper.item.GetItemResponseMapper;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsResponse;
import com.github.psbds.service.item.ListItemsService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.List;

@QuarkusComponentTest
class ListItemsServiceTest {

    private static final String TEST_USER_ID = "test-user-123";

    @Inject
    ListItemsService listItemsService;

    @InjectMock
    ItemRepository itemRepository;

    @InjectMock
    GetItemResponseMapper getItemResponseMapper;

    @Test
    void listByUserId_when_userHasItems_should_returnMappedResponse() {
        // Arrange
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        List<Item> items = Arrays.asList(item1, item2);
        
        ItemResourceGetItemsItemResponse response1 = new ItemResourceGetItemsItemResponse(1L, 101L, 2, 29.99, Arrays.asList());
        ItemResourceGetItemsItemResponse response2 = new ItemResourceGetItemsItemResponse(2L, 102L, 1, 49.99, Arrays.asList());
        
        when(itemRepository.findByUserId(TEST_USER_ID)).thenReturn(items);
        when(getItemResponseMapper.map(item1)).thenReturn(response1);
        when(getItemResponseMapper.map(item2)).thenReturn(response2);

        // Act
        ItemResourceGetItemsResponse result = listItemsService.listByUserId(TEST_USER_ID);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getItems(), "Items list should not be null");
        assertEquals(2, result.getItems().size(), "Should return 2 items");
        assertEquals(response1, result.getItems().get(0), "First item should match");
        assertEquals(response2, result.getItems().get(1), "Second item should match");
        
        verify(itemRepository).findByUserId(TEST_USER_ID);
        verify(getItemResponseMapper).map(item1);
        verify(getItemResponseMapper).map(item2);
    }

    @Test
    void listByUserId_when_userHasNoItems_should_returnEmptyResponse() {
        // Arrange
        when(itemRepository.findByUserId(TEST_USER_ID)).thenReturn(Arrays.asList());

        // Act
        ItemResourceGetItemsResponse result = listItemsService.listByUserId(TEST_USER_ID);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getItems(), "Items list should not be null");
        assertEquals(0, result.getItems().size(), "Should return empty list");
        
        verify(itemRepository).findByUserId(TEST_USER_ID);
        verifyNoInteractions(getItemResponseMapper);
    }

    @Test
    void testServiceHasApplicationScopedAnnotation() {
        // Arrange
        Class<ListItemsService> serviceClass = ListItemsService.class;

        // Act
        ApplicationScoped annotation = serviceClass.getAnnotation(ApplicationScoped.class);

        // Assert
        assertNotNull(annotation, 
            "ListItemsService should have @ApplicationScoped annotation");
    }
}