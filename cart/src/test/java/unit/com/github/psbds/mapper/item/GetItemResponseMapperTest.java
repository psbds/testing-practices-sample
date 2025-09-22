package unit.com.github.psbds.mapper.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.domain.item.Item;
import com.github.psbds.domain.item.ItemMetadata;
import com.github.psbds.mapper.item.GetItemResponseMapper;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsMetadataResponse;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Arrays;

class GetItemResponseMapperTest {

    private static final Long TEST_ITEM_ID = 1L;
    private static final Long TEST_PRODUCT_ID = 101L;
    private static final int TEST_QUANTITY = 2;
    private static final BigDecimal TEST_PRICE = new BigDecimal(29.99);

    private GetItemResponseMapper mapper = new GetItemResponseMapper();

    @Test
    void map_when_itemWithMetadataProvided_should_returnMappedResponse() {
        // Arrange
        Item item = createMockItem();
        ItemMetadata metadata1 = createMockMetadata("color", "red");
        ItemMetadata metadata2 = createMockMetadata("size", "large");
        when(item.getMetadata()).thenReturn(Arrays.asList(metadata1, metadata2));

        // Act
        ItemResourceGetItemsItemResponse result = mapper.map(item);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(TEST_ITEM_ID, result.getId(), "ID should match");
        assertEquals(TEST_PRODUCT_ID, result.getProductId(), "Product ID should match");
        assertEquals(TEST_QUANTITY, result.getQuantity(), "Quantity should match");
        assertEquals(TEST_PRICE, result.getPrice(), "Price should match");
        
        assertNotNull(result.getMetadata(), "Metadata should not be null");
        assertEquals(2, result.getMetadata().size(), "Should have 2 metadata items");
        
        ItemResourceGetItemsMetadataResponse resultMetadata1 = result.getMetadata().get(0);
        assertEquals("color", resultMetadata1.getKey(), "First metadata key should match");
        assertEquals("red", resultMetadata1.getValue(), "First metadata value should match");
        
        ItemResourceGetItemsMetadataResponse resultMetadata2 = result.getMetadata().get(1);
        assertEquals("size", resultMetadata2.getKey(), "Second metadata key should match");
        assertEquals("large", resultMetadata2.getValue(), "Second metadata value should match");
    }

    @Test
    void map_when_itemWithoutMetadataProvided_should_returnResponseWithEmptyMetadata() {
        // Arrange
        Item item = createMockItem();
        when(item.getMetadata()).thenReturn(Arrays.asList());

        // Act
        ItemResourceGetItemsItemResponse result = mapper.map(item);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(TEST_ITEM_ID, result.getId(), "ID should match");
        assertEquals(TEST_PRODUCT_ID, result.getProductId(), "Product ID should match");
        assertEquals(TEST_QUANTITY, result.getQuantity(), "Quantity should match");
        assertEquals(TEST_PRICE, result.getPrice(), "Price should match");
        
        assertNotNull(result.getMetadata(), "Metadata should not be null");
        assertEquals(0, result.getMetadata().size(), "Should have no metadata items");
    }

    @Test
    void testMapperHasApplicationScopedAnnotation() {
        // Arrange
        Class<GetItemResponseMapper> mapperClass = GetItemResponseMapper.class;

        // Act
        ApplicationScoped annotation = mapperClass.getAnnotation(ApplicationScoped.class);

        // Assert
        assertNotNull(annotation, 
            "GetItemResponseMapper should have @ApplicationScoped annotation");
    }

    private Item createMockItem() {
        Item item = mock(Item.class);
        when(item.getId()).thenReturn(TEST_ITEM_ID);
        when(item.getProductId()).thenReturn(TEST_PRODUCT_ID);
        when(item.getQuantity()).thenReturn(TEST_QUANTITY);
        when(item.getPrice()).thenReturn(TEST_PRICE);
        return item;
    }

    private ItemMetadata createMockMetadata(String key, String value) {
        ItemMetadata metadata = mock(ItemMetadata.class);
        when(metadata.getKey()).thenReturn(key);
        when(metadata.getValue()).thenReturn(value);
        return metadata;
    }
}