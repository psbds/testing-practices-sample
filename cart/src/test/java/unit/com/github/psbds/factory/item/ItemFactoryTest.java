package unit.com.github.psbds.factory.item;

import com.github.psbds.domain.item.Item;
import com.github.psbds.factory.item.ItemFactory;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusComponentTest
class ItemFactoryTest {

    private static final String TEST_USER_ID = "test-user-123";
    private static final Long TEST_PRODUCT_ID = 12345L;
    private static final Integer TEST_QUANTITY = 5;
    private static final BigDecimal TEST_PRICE = new BigDecimal(99.99);

    @Inject
    ItemFactory itemFactory;

    @Test
    void create_when_validDataIsProvided_should_createItemWithCorrectProperties() {
        // Arrange
        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);
        when(mockRequest.getMetadata()).thenReturn(List.of());

        // Act
        Item result = itemFactory.create(TEST_USER_ID, mockRequest);

        // Assert
        assertNotNull(result, "Created item should not be null");
        assertEquals(TEST_USER_ID, result.getUserId(), "Item should have correct user ID");
        assertEquals(TEST_PRODUCT_ID, result.getProductId(), "Item should have correct product ID");
        assertEquals(TEST_QUANTITY, result.getQuantity(), "Item should have correct quantity");
        assertEquals(TEST_PRICE, result.getPrice(), "Item should have correct price");
        assertNotNull(result.getMetadata(), "Metadata list should not be null");
        assertTrue(result.getMetadata().isEmpty(), "Metadata list should be empty when no metadata provided");
    }

    @Test
    void create_when_metadataIsProvided_should_addMetadataToItem() {
        // Arrange
        ItemResourcePostItemMetadataRequest mockMetadata1 = mock(ItemResourcePostItemMetadataRequest.class);
        when(mockMetadata1.getKey()).thenReturn("color");
        when(mockMetadata1.getValue()).thenReturn("red");

        ItemResourcePostItemMetadataRequest mockMetadata2 = mock(ItemResourcePostItemMetadataRequest.class);
        when(mockMetadata2.getKey()).thenReturn("size");
        when(mockMetadata2.getValue()).thenReturn("large");

        ItemResourcePostItemRequest mockRequest = mock(ItemResourcePostItemRequest.class);
        when(mockRequest.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(mockRequest.getQuantity()).thenReturn(TEST_QUANTITY);
        when(mockRequest.getPrice()).thenReturn(TEST_PRICE);
        when(mockRequest.getMetadata()).thenReturn(List.of(mockMetadata1, mockMetadata2));

        // Act
        Item result = itemFactory.create(TEST_USER_ID, mockRequest);

        // Assert
        assertNotNull(result, "Created item should not be null");
        assertEquals(2, result.getMetadata().size(), "Item should have 2 metadata entries");

        assertEquals("color", result.getMetadata().get(0).getKey(), "First metadata should have correct key");
        assertEquals("red", result.getMetadata().get(0).getValue(), "First metadata should have correct value");
        assertEquals(result, result.getMetadata().get(0).getItem(), "First metadata should reference the item");

        assertEquals("size", result.getMetadata().get(1).getKey(), "Second metadata should have correct key");
        assertEquals("large", result.getMetadata().get(1).getValue(), "Second metadata should have correct value");
        assertEquals(result, result.getMetadata().get(1).getItem(), "Second metadata should reference the item");
    }
}