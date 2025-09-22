package unit.com.github.psbds.resource.item.model.getitems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsMetadataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class ItemResourceGetItemsItemResponseTest {

    private static final Long TEST_ID = 1L;
    private static final Long TEST_PRODUCT_ID = 101L;
    private static final Integer TEST_QUANTITY = 2;
    private static final BigDecimal TEST_PRICE = new BigDecimal(29.99);

    @Test
    void constructor_when_allParametersProvided_should_createObjectSuccessfully() {
        // Arrange
        ItemResourceGetItemsMetadataResponse metadata = new ItemResourceGetItemsMetadataResponse("color", "red");
        List<ItemResourceGetItemsMetadataResponse> metadataList = Arrays.asList(metadata);

        // Act
        ItemResourceGetItemsItemResponse response = new ItemResourceGetItemsItemResponse(TEST_ID, TEST_PRODUCT_ID, TEST_QUANTITY, TEST_PRICE, metadataList);

        // Assert
        assertEquals(TEST_ID, response.getId(), "ID should be set correctly");
        assertEquals(TEST_PRODUCT_ID, response.getProductId(), "Product ID should be set correctly");
        assertEquals(TEST_QUANTITY, response.getQuantity(), "Quantity should be set correctly");
        assertEquals(TEST_PRICE, response.getPrice(), "Price should be set correctly");
        assertEquals(metadataList, response.getMetadata(), "Metadata should be set correctly");
    }

    @Test
    void noArgsConstructor_should_createObjectSuccessfully() {
        // Act
        ItemResourceGetItemsItemResponse response = new ItemResourceGetItemsItemResponse();

        // Assert
        assertNotNull(response, "Object should be created successfully");
    }

    @Test
    void setters_when_valuesProvided_should_setFieldsCorrectly() {
        // Arrange
        ItemResourceGetItemsItemResponse response = new ItemResourceGetItemsItemResponse();
        ItemResourceGetItemsMetadataResponse metadata = new ItemResourceGetItemsMetadataResponse("size", "large");
        List<ItemResourceGetItemsMetadataResponse> metadataList = Arrays.asList(metadata);

        // Act
        response.setId(TEST_ID);
        response.setProductId(TEST_PRODUCT_ID);
        response.setQuantity(TEST_QUANTITY);
        response.setPrice(TEST_PRICE);
        response.setMetadata(metadataList);

        // Assert
        assertEquals(TEST_ID, response.getId(), "ID should be set correctly");
        assertEquals(TEST_PRODUCT_ID, response.getProductId(), "Product ID should be set correctly");
        assertEquals(TEST_QUANTITY, response.getQuantity(), "Quantity should be set correctly");
        assertEquals(TEST_PRICE, response.getPrice(), "Price should be set correctly");
        assertEquals(metadataList, response.getMetadata(), "Metadata should be set correctly");
    }

    @Test
    void testIdFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field idField = ItemResourceGetItemsItemResponse.class.getDeclaredField("id");

        // Act
        JsonProperty jsonPropertyAnnotation = idField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "id field should have @JsonProperty annotation");
        assertEquals("id", jsonPropertyAnnotation.value(), 
            "id field should have correct JSON property name");
    }

    @Test
    void testProductIdFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field productIdField = ItemResourceGetItemsItemResponse.class.getDeclaredField("productId");

        // Act
        JsonProperty jsonPropertyAnnotation = productIdField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "productId field should have @JsonProperty annotation");
        assertEquals("product_id", jsonPropertyAnnotation.value(), 
            "productId field should have correct JSON property name");
    }

    @Test
    void testQuantityFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field quantityField = ItemResourceGetItemsItemResponse.class.getDeclaredField("quantity");

        // Act
        JsonProperty jsonPropertyAnnotation = quantityField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "quantity field should have @JsonProperty annotation");
        assertEquals("quantity", jsonPropertyAnnotation.value(), 
            "quantity field should have correct JSON property name");
    }

    @Test
    void testPriceFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field priceField = ItemResourceGetItemsItemResponse.class.getDeclaredField("price");

        // Act
        JsonProperty jsonPropertyAnnotation = priceField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "price field should have @JsonProperty annotation");
        assertEquals("price", jsonPropertyAnnotation.value(), 
            "price field should have correct JSON property name");
    }

    @Test
    void testMetadataFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field metadataField = ItemResourceGetItemsItemResponse.class.getDeclaredField("metadata");

        // Act
        JsonProperty jsonPropertyAnnotation = metadataField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "metadata field should have @JsonProperty annotation");
        assertEquals("metadata", jsonPropertyAnnotation.value(), 
            "metadata field should have correct JSON property name");
    }
}