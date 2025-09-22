package unit.com.github.psbds.domain.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.github.psbds.domain.item.Item;
import com.github.psbds.domain.item.ItemMetadata;
import com.github.psbds.errors.exception.BusinessException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

class ItemMetadataTest {

    private static final String VALID_KEY = "color";
    private static final String VALID_VALUE = "red";
    private static final String INVALID_VALUE_ERROR_CODE = "INVALID_VALUE";

    @Test
    void constructor_when_allValidParametersProvided_should_createItemMetadataSuccessfully() {
        // Arrange
        Item mockItem = mock(Item.class);

        // Act
        ItemMetadata itemMetadata = new ItemMetadata(VALID_KEY, VALID_VALUE, mockItem);

        // Assert
        assertEquals(VALID_KEY, itemMetadata.getKey(), "Key should be set correctly");
        assertEquals(VALID_VALUE, itemMetadata.getValue(), "Value should be set correctly");
        assertEquals(mockItem, itemMetadata.getItem(), "Item should be set correctly");
    }

    @Test
    void constructor_when_keyIsNull_should_throwBusinessException() {
        // Arrange
        String nullKey = null;
        Item mockItem = mock(Item.class);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(nullKey, VALID_VALUE, mockItem),
            "Should throw BusinessException when key is null");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Key cannot be null or empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_keyIsEmpty_should_throwBusinessException() {
        // Arrange
        String emptyKey = "";
        Item mockItem = mock(Item.class);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(emptyKey, VALID_VALUE, mockItem),
            "Should throw BusinessException when key is empty");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Key cannot be null or empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_keyIsBlank_should_throwBusinessException() {
        // Arrange
        String blankKey = "   ";
        Item mockItem = mock(Item.class);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(blankKey, VALID_VALUE, mockItem),
            "Should throw BusinessException when key is blank");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Key cannot be null or empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_valueIsNull_should_throwBusinessException() {
        // Arrange
        String nullValue = null;
        Item mockItem = mock(Item.class);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(VALID_KEY, nullValue, mockItem),
            "Should throw BusinessException when value is null");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Value cannot be null or empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_valueIsEmpty_should_throwBusinessException() {
        // Arrange
        String emptyValue = "";
        Item mockItem = mock(Item.class);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(VALID_KEY, emptyValue, mockItem),
            "Should throw BusinessException when value is empty");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Value cannot be null or empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_valueIsBlank_should_throwBusinessException() {
        // Arrange
        String blankValue = "   ";
        Item mockItem = mock(Item.class);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(VALID_KEY, blankValue, mockItem),
            "Should throw BusinessException when value is blank");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Value cannot be null or empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_itemIsNull_should_throwBusinessException() {
        // Arrange
        Item nullItem = null;

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new ItemMetadata(VALID_KEY, VALID_VALUE, nullItem),
            "Should throw BusinessException when item is null");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Item cannot be null", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void testEntityAnnotation() {
        // Arrange
        Class<ItemMetadata> itemMetadataClass = ItemMetadata.class;

        // Act
        Entity entityAnnotation = itemMetadataClass.getAnnotation(Entity.class);

        // Assert
        assertNotNull(entityAnnotation, "ItemMetadata class should have @Entity annotation");
    }

    @Test
    void testKeyFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field keyField = ItemMetadata.class.getDeclaredField("key");

        // Act
        Column columnAnnotation = keyField.getAnnotation(Column.class);

        // Assert
        assertNotNull(columnAnnotation, "key field should have @Column annotation");
        assertEquals("[key]", columnAnnotation.name(), 
            "key field should have correct column name with brackets");
        assertFalse(columnAnnotation.nullable(), 
            "key field should be marked as not nullable");
    }

    @Test
    void testValueFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field valueField = ItemMetadata.class.getDeclaredField("value");

        // Act
        Column columnAnnotation = valueField.getAnnotation(Column.class);

        // Assert
        assertNotNull(columnAnnotation, "value field should have @Column annotation");
        assertEquals("[value]", columnAnnotation.name(), 
            "value field should have correct column name with brackets");
        assertFalse(columnAnnotation.nullable(), 
            "value field should be marked as not nullable");
    }

    @Test
    void testItemFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field itemField = ItemMetadata.class.getDeclaredField("item");

        // Act
        ManyToOne manyToOneAnnotation = itemField.getAnnotation(ManyToOne.class);
        JoinColumn joinColumnAnnotation = itemField.getAnnotation(JoinColumn.class);

        // Assert
        assertNotNull(manyToOneAnnotation, "item field should have @ManyToOne annotation");
        
        assertNotNull(joinColumnAnnotation, "item field should have @JoinColumn annotation");
        assertEquals("item_id", joinColumnAnnotation.name(), 
            "item field should have correct join column name");
    }
}