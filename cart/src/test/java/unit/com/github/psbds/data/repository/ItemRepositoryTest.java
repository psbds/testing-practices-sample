package unit.com.github.psbds.data.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.data.repository.BaseRepository;
import com.github.psbds.data.repository.ItemRepository;
import com.github.psbds.domain.item.Item;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.List;

@QuarkusComponentTest
class ItemRepositoryTest {

    private static final String TEST_USER_ID = "test-user-123";

    @Inject
    ItemRepository repository;

    @InjectMock
    BaseRepository<Item, Long> baseRepository;

    @Test
    void persist_when_entityProvided_should_delegateToBaseRepository() {
        // Arrange
        Item mockItem = mock(Item.class);

        // Act
        repository.persist(mockItem);

        // Assert
        verify(baseRepository).persist(mockItem);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findByUserId_when_userIdProvided_should_returnItemsList() {
        // Arrange
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        List<Item> expectedItems = Arrays.asList(item1, item2);
        
        PanacheQuery<Item> mockQuery = mock(PanacheQuery.class);
        when(baseRepository.find("userId", TEST_USER_ID)).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(expectedItems);

        // Act
        List<Item> result = repository.findByUserId(TEST_USER_ID);

        // Assert
        assertEquals(expectedItems, result, "Should return the expected list of items");
        verify(baseRepository).find("userId", TEST_USER_ID);
        verify(mockQuery).list();
    }

    @Test
    @SuppressWarnings("unchecked")
    void findByUserId_when_userHasNoItems_should_returnEmptyList() {
        // Arrange
        List<Item> emptyList = Arrays.asList();
        
        PanacheQuery<Item> mockQuery = mock(PanacheQuery.class);
        when(baseRepository.find("userId", TEST_USER_ID)).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(emptyList);

        // Act
        List<Item> result = repository.findByUserId(TEST_USER_ID);

        // Assert
        assertEquals(emptyList, result, "Should return an empty list when user has no items");
        verify(baseRepository).find("userId", TEST_USER_ID);
        verify(mockQuery).list();
    }

    @Test
    void testRepositoryHasApplicationScopedAnnotation() {
        // Arrange
        Class<ItemRepository> repositoryClass = ItemRepository.class;

        // Act
        ApplicationScoped annotation = repositoryClass.getAnnotation(ApplicationScoped.class);

        // Assert
        assertNotNull(annotation, 
            "ItemRepository should have @ApplicationScoped annotation");
    }
}