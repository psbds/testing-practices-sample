package unit.com.github.psbds.resource.item.model.postitem;

import com.github.psbds.resource.item.model.postitem.ItemResourcePostItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PostItemResponseTest {

    private static final Long TEST_ID = 12345L;
    private static final Long NEW_ID = 67890L;

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        ItemResourcePostItemResponse response = new ItemResourcePostItemResponse();

        // Assert
        assertNull(response.getId(), "id should be null when using no-args constructor");
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        ItemResourcePostItemResponse response = new ItemResourcePostItemResponse(TEST_ID);

        // Assert
        assertEquals(TEST_ID, response.getId(), 
            "id should be set correctly by all-args constructor");
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        ItemResourcePostItemResponse response = new ItemResourcePostItemResponse();
        
        // Act
        response.setId(TEST_ID);
        
        // Assert
        assertEquals(TEST_ID, response.getId(), 
            "getId should return the value set by setId");
        
        // Act - update with new value
        response.setId(NEW_ID);
        
        // Assert
        assertEquals(NEW_ID, response.getId(), 
            "getId should return the updated value set by setId");
    }

    @Test
    void testIdFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field idField = ItemResourcePostItemResponse.class.getDeclaredField("id");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = idField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "id field should have @JsonProperty annotation");
        assertEquals("id", jsonProperty.value(), 
            "id field should have correct JsonProperty value");
    }
}