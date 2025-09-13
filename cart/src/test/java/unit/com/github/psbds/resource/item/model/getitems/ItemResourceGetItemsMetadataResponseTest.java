package unit.com.github.psbds.resource.item.model.getitems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsMetadataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;

class ItemResourceGetItemsMetadataResponseTest {

    private static final String TEST_KEY = "color";
    private static final String TEST_VALUE = "red";

    @Test
    void constructor_when_allParametersProvided_should_createObjectSuccessfully() {
        // Act
        ItemResourceGetItemsMetadataResponse response = new ItemResourceGetItemsMetadataResponse(TEST_KEY, TEST_VALUE);

        // Assert
        assertEquals(TEST_KEY, response.getKey(), "Key should be set correctly");
        assertEquals(TEST_VALUE, response.getValue(), "Value should be set correctly");
    }

    @Test
    void noArgsConstructor_should_createObjectSuccessfully() {
        // Act
        ItemResourceGetItemsMetadataResponse response = new ItemResourceGetItemsMetadataResponse();

        // Assert
        assertNotNull(response, "Object should be created successfully");
    }

    @Test
    void setters_when_valuesProvided_should_setFieldsCorrectly() {
        // Arrange
        ItemResourceGetItemsMetadataResponse response = new ItemResourceGetItemsMetadataResponse();

        // Act
        response.setKey(TEST_KEY);
        response.setValue(TEST_VALUE);

        // Assert
        assertEquals(TEST_KEY, response.getKey(), "Key should be set correctly");
        assertEquals(TEST_VALUE, response.getValue(), "Value should be set correctly");
    }

    @Test
    void testKeyFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field keyField = ItemResourceGetItemsMetadataResponse.class.getDeclaredField("key");

        // Act
        JsonProperty jsonPropertyAnnotation = keyField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "key field should have @JsonProperty annotation");
        assertEquals("key", jsonPropertyAnnotation.value(), 
            "key field should have correct JSON property name");
    }

    @Test
    void testValueFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field valueField = ItemResourceGetItemsMetadataResponse.class.getDeclaredField("value");

        // Act
        JsonProperty jsonPropertyAnnotation = valueField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "value field should have @JsonProperty annotation");
        assertEquals("value", jsonPropertyAnnotation.value(), 
            "value field should have correct JSON property name");
    }
}