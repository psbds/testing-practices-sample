package unit.com.github.psbds.resource.item.model;

import com.github.psbds.resource.item.model.PostItemMetadataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostItemMetadataRequestTest {

    private static final String TEST_KEY = "testKey";
    private static final String TEST_VALUE = "testValue";

    @Test
    void testGettersAndSetters() {
        // Arrange
        PostItemMetadataRequest request = new PostItemMetadataRequest();
        
        // Act
        request.setKey(TEST_KEY);
        request.setValue(TEST_VALUE);
        
        // Assert
        assertEquals(TEST_KEY, request.getKey(), 
            "getKey should return the value set by setKey");
        assertEquals(TEST_VALUE, request.getValue(), 
            "getValue should return the value set by setValue");
    }

    @Test
    void testKeyFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field keyField = PostItemMetadataRequest.class.getDeclaredField("key");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = keyField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "key field should have @JsonProperty annotation");
        assertEquals("key", jsonProperty.value(), 
            "key field should have correct JsonProperty value");
        
        // Assert NotNull annotation
        NotNull notNull = keyField.getAnnotation(NotNull.class);
        assertEquals("Key is required", notNull.message(), 
            "key field should have correct NotNull message");
        
        // Assert NotBlank annotation
        NotBlank notBlank = keyField.getAnnotation(NotBlank.class);
        assertEquals("Key cannot be blank", notBlank.message(), 
            "key field should have correct NotBlank message");
        
        // Assert Size annotation
        Size size = keyField.getAnnotation(Size.class);
        assertEquals(1, size.min(), "key field should have correct Size min value");
        assertEquals(255, size.max(), "key field should have correct Size max value");
        assertEquals("Key must be between 1 and 255 characters", size.message(), 
            "key field should have correct Size message");
    }

    @Test
    void testValueFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field valueField = PostItemMetadataRequest.class.getDeclaredField("value");
        
        // Assert JsonProperty annotation
        JsonProperty jsonProperty = valueField.getAnnotation(JsonProperty.class);
        assertNotNull(jsonProperty, "value field should have @JsonProperty annotation");
        assertEquals("value", jsonProperty.value(), 
            "value field should have correct JsonProperty value");
        
        // Assert NotNull annotation
        NotNull notNull = valueField.getAnnotation(NotNull.class);
        assertEquals("Value is required", notNull.message(), 
            "value field should have correct NotNull message");
        
        // Assert NotBlank annotation
        NotBlank notBlank = valueField.getAnnotation(NotBlank.class);
        assertEquals("Value cannot be blank", notBlank.message(), 
            "value field should have correct NotBlank message");
        
        // Assert Size annotation
        Size size = valueField.getAnnotation(Size.class);
        assertEquals(1, size.min(), "value field should have correct Size min value");
        assertEquals(255, size.max(), "value field should have correct Size max value");
        assertEquals("Value must be between 1 and 255 characters", size.message(), 
            "value field should have correct Size message");
    }
}