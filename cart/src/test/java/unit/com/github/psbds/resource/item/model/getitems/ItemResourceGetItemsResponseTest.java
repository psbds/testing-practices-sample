package unit.com.github.psbds.resource.item.model.getitems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsResponse;
import com.github.psbds.resource.item.model.getitems.ItemResourceGetItemsItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class ItemResourceGetItemsResponseTest {

    @Test
    void constructor_when_allParametersProvided_should_createObjectSuccessfully() {
        // Arrange
        ItemResourceGetItemsItemResponse item1 = new ItemResourceGetItemsItemResponse(1L, 101L, 2, new BigDecimal(29.99), Arrays.asList());
        ItemResourceGetItemsItemResponse item2 = new ItemResourceGetItemsItemResponse(2L, 102L, 1, new BigDecimal(49.99), Arrays.asList());
        List<ItemResourceGetItemsItemResponse> items = Arrays.asList(item1, item2);

        // Act
        ItemResourceGetItemsResponse response = new ItemResourceGetItemsResponse(items);

        // Assert
        assertEquals(items, response.getItems(), "Items should be set correctly");
    }

    @Test
    void noArgsConstructor_should_createObjectSuccessfully() {
        // Act
        ItemResourceGetItemsResponse response = new ItemResourceGetItemsResponse();

        // Assert
        assertNotNull(response, "Object should be created successfully");
    }

    @Test
    void setter_when_itemsProvided_should_setItemsCorrectly() {
        // Arrange
        ItemResourceGetItemsResponse response = new ItemResourceGetItemsResponse();
        ItemResourceGetItemsItemResponse item = new ItemResourceGetItemsItemResponse(1L, 101L, 2, new BigDecimal(29.99), Arrays.asList());
        List<ItemResourceGetItemsItemResponse> items = Arrays.asList(item);

        // Act
        response.setItems(items);

        // Assert
        assertEquals(items, response.getItems(), "Items should be set correctly");
    }

    @Test
    void testItemsFieldJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        Field itemsField = ItemResourceGetItemsResponse.class.getDeclaredField("items");

        // Act
        JsonProperty jsonPropertyAnnotation = itemsField.getAnnotation(JsonProperty.class);

        // Assert
        assertNotNull(jsonPropertyAnnotation, "items field should have @JsonProperty annotation");
        assertEquals("items", jsonPropertyAnnotation.value(), 
            "items field should have correct JSON property name");
    }
}