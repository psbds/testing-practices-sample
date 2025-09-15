package unit.com.github.psbds.resource.item.model.postitem;

import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemRequest;
import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemMetadataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostItemRequestTest {

    private static final Long TEST_PRODUCT_ID = 12345L;
    private static final Integer TEST_QUANTITY = 10;
    private static final BigDecimal TEST_PRICE = new BigDecimal(99.99);

    @Test
    void testGettersAndSetters() {
        // Arrange
        ItemResourcePostItemRequest request = new ItemResourcePostItemRequest();
        List<ItemResourcePostItemMetadataRequest> testMetadata = new ArrayList<>();
        
        // Act
        request.setProductId(TEST_PRODUCT_ID);
        request.setQuantity(TEST_QUANTITY);
        request.setPrice(TEST_PRICE);
        request.setMetadata(testMetadata);
        
        // Assert
        assertEquals(TEST_PRODUCT_ID, request.getProductId(), 
            "getProductId should return the value set by setProductId");
        assertEquals(TEST_QUANTITY, request.getQuantity(), 
            "getQuantity should return the value set by setQuantity");
        assertEquals(TEST_PRICE, request.getPrice(), 
            "getPrice should return the value set by setPrice");
        assertEquals(testMetadata, request.getMetadata(), 
            "getMetadata should return the value set by setMetadata");
    }

    @Test
    void testProductIdFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field productIdField = ItemResourcePostItemRequest.class.getDeclaredField("productId");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = productIdField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "productId field should have @JsonProperty annotation");
        assertEquals("product_id", jsonProperty.value(), 
            "productId field should have correct JsonProperty value");
        
        // Assert NotNull annotation
        NotNull notNull = productIdField.getAnnotation(NotNull.class);
        assertEquals("Product ID is required", notNull.message(), 
            "productId field should have correct NotNull message");
    }

    @Test
    void testQuantityFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field quantityField = ItemResourcePostItemRequest.class.getDeclaredField("quantity");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = quantityField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "quantity field should have @JsonProperty annotation");
        assertEquals("quantity", jsonProperty.value(), 
            "quantity field should have correct JsonProperty value");
        
        // Assert NotNull annotation
        NotNull notNull = quantityField.getAnnotation(NotNull.class);
        assertEquals("Quantity is required", notNull.message(), 
            "quantity field should have correct NotNull message");
        
        // Assert Positive annotation
        Positive positive = quantityField.getAnnotation(Positive.class);
        assertEquals("Quantity must be positive", positive.message(), 
            "quantity field should have correct Positive message");
    }

    @Test
    void testPriceFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field priceField = ItemResourcePostItemRequest.class.getDeclaredField("price");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = priceField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "price field should have @JsonProperty annotation");
        assertEquals("price", jsonProperty.value(), 
            "price field should have correct JsonProperty value");
        
        // Assert NotNull annotation
        NotNull notNull = priceField.getAnnotation(NotNull.class);
        assertEquals("Price is required", notNull.message(), 
            "price field should have correct NotNull message");
        
        // Assert PositiveOrZero annotation
        PositiveOrZero positiveOrZero = priceField.getAnnotation(PositiveOrZero.class);
        assertEquals("Price must be positive or zero", positiveOrZero.message(), 
            "price field should have correct PositiveOrZero message");
    }

    @Test
    void testMetadataFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field metadataField = ItemResourcePostItemRequest.class.getDeclaredField("metadata");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = metadataField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "metadata field should have @JsonProperty annotation");
        assertEquals("metadata", jsonProperty.value(), 
            "metadata field should have correct JsonProperty value");
        
        // Assert NotNull annotation
        NotNull notNull = metadataField.getAnnotation(NotNull.class);
        assertEquals("Metadata is required", notNull.message(), 
            "metadata field should have correct NotNull message");
    }
}