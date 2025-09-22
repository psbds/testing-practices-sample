package unit.com.github.psbds.domain.item;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.github.psbds.domain.item.Item;
import com.github.psbds.domain.item.ItemMetadata;
import com.github.psbds.errors.exception.BusinessException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

class ItemTest {

    private static final String VALID_USER_ID = "user123";
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final int VALID_QUANTITY = 5;
    private static final BigDecimal VALID_PRICE = new BigDecimal("99.99");
    private static final String INVALID_VALUE_ERROR_CODE = "INVALID_VALUE";

    @Test
    void constructor_when_allValidParametersProvided_should_createItemSuccessfully() {
        // Arrange & Act
        Item item = new Item(VALID_USER_ID, VALID_PRODUCT_ID, VALID_QUANTITY, VALID_PRICE);

        // Assert
        assertEquals(VALID_USER_ID, item.getUserId(), "UserId should be set correctly");
        assertEquals(VALID_PRODUCT_ID, item.getProductId(), "ProductId should be set correctly");
        assertEquals(VALID_QUANTITY, item.getQuantity(), "Quantity should be set correctly");
        assertEquals(VALID_PRICE, item.getPrice(), "Price should be set correctly");
        assertNotNull(item.getMetadata(), "Metadata list should be initialized");
        assertTrue(item.getMetadata().isEmpty(), "Metadata list should be empty initially");
    }

    @Test
    void constructor_when_userIdIsNull_should_throwBusinessException() {
        // Arrange
        String nullUserId = null;

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(nullUserId, VALID_PRODUCT_ID, VALID_QUANTITY, VALID_PRICE),
            "Should throw BusinessException when userId is null");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("User ID is required and cannot be empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_userIdIsEmpty_should_throwBusinessException() {
        // Arrange
        String emptyUserId = "";

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(emptyUserId, VALID_PRODUCT_ID, VALID_QUANTITY, VALID_PRICE),
            "Should throw BusinessException when userId is empty");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("User ID is required and cannot be empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_userIdIsBlank_should_throwBusinessException() {
        // Arrange
        String blankUserId = "   ";

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(blankUserId, VALID_PRODUCT_ID, VALID_QUANTITY, VALID_PRICE),
            "Should throw BusinessException when userId is blank");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("User ID is required and cannot be empty", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_quantityIsZero_should_throwBusinessException() {
        // Arrange
        int zeroQuantity = 0;

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(VALID_USER_ID, VALID_PRODUCT_ID, zeroQuantity, VALID_PRICE),
            "Should throw BusinessException when quantity is zero");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Quantity should be greater than zero", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_quantityIsNegative_should_throwBusinessException() {
        // Arrange
        int negativeQuantity = -1;

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(VALID_USER_ID, VALID_PRODUCT_ID, negativeQuantity, VALID_PRICE),
            "Should throw BusinessException when quantity is negative");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Quantity should be greater than zero", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_priceIsNegative_should_throwBusinessException() {
        // Arrange
        BigDecimal negativePrice = new BigDecimal("-0.01");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(VALID_USER_ID, VALID_PRODUCT_ID, VALID_QUANTITY, negativePrice),
            "Should throw BusinessException when price is negative");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Price should not be null or negative", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_priceIsNull_should_throwBusinessException() {
        // Arrange
        BigDecimal nullPrice = null;

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
            () -> new Item(VALID_USER_ID, VALID_PRODUCT_ID, VALID_QUANTITY, nullPrice),
            "Should throw BusinessException when price is null");
        
        assertEquals(INVALID_VALUE_ERROR_CODE, exception.getErrorCode(), 
            "Should have correct error code");
        assertEquals("Price should not be null or negative", exception.getErrorMessage(), 
            "Should have correct error message");
    }

    @Test
    void constructor_when_priceIsZero_should_createItemSuccessfully() {
        // Arrange
        BigDecimal zeroPrice = BigDecimal.ZERO;

        // Act
        Item item = new Item(VALID_USER_ID, VALID_PRODUCT_ID, VALID_QUANTITY, zeroPrice);

        // Assert
        assertEquals(zeroPrice, item.getPrice(), "Price of zero should be allowed");
    }

    @Test
    void addMetadata_when_validKeyAndValueProvided_should_addMetadataToList() {
        // Arrange
        Item item = new Item(VALID_USER_ID, VALID_PRODUCT_ID, VALID_QUANTITY, VALID_PRICE);
        String key = "color";
        String value = "red";
        int initialSize = item.getMetadata().size();

        // Act
        item.addMetadata(key, value);

        // Assert
        assertEquals(initialSize + 1, item.getMetadata().size(), 
            "Metadata list size should increase by one");
        
        ItemMetadata addedMetadata = item.getMetadata().get(item.getMetadata().size() - 1);
        assertEquals(key, addedMetadata.getKey(), "Added metadata should have correct key");
        assertEquals(value, addedMetadata.getValue(), "Added metadata should have correct value");
        assertEquals(item, addedMetadata.getItem(), "Added metadata should reference the correct item");
    }

    @Test
    void testEntityAnnotation() {
        // Arrange
        Class<Item> itemClass = Item.class;

        // Act
        Entity entityAnnotation = itemClass.getAnnotation(Entity.class);

        // Assert
        assertNotNull(entityAnnotation, "Item class should have @Entity annotation");
    }

    @Test
    void testUserIdFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field userIdField = Item.class.getDeclaredField("userId");

        // Act
        Column columnAnnotation = userIdField.getAnnotation(Column.class);

        // Assert
        assertNotNull(columnAnnotation, "userId field should have @Column annotation");
        assertEquals("user_id", columnAnnotation.name(), 
            "userId field should have correct column name");
        assertFalse(columnAnnotation.nullable(), 
            "userId field should be marked as not nullable");
    }

    @Test
    void testProductIdFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field productIdField = Item.class.getDeclaredField("productId");

        // Act
        Column columnAnnotation = productIdField.getAnnotation(Column.class);

        // Assert
        assertNotNull(columnAnnotation, "productId field should have @Column annotation");
        assertEquals("product_id", columnAnnotation.name(), 
            "productId field should have correct column name");
        assertFalse(columnAnnotation.nullable(), 
            "productId field should be marked as not nullable");
    }

    @Test
    void testQuantityFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field quantityField = Item.class.getDeclaredField("quantity");

        // Act
        Column columnAnnotation = quantityField.getAnnotation(Column.class);

        // Assert
        assertNotNull(columnAnnotation, "quantity field should have @Column annotation");
        assertEquals("quantity", columnAnnotation.name(), 
            "quantity field should have correct column name");
        assertFalse(columnAnnotation.nullable(), 
            "quantity field should be marked as not nullable");
    }

    @Test
    void testPriceFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field priceField = Item.class.getDeclaredField("price");

        // Act
        Column columnAnnotation = priceField.getAnnotation(Column.class);

        // Assert
        assertNotNull(columnAnnotation, "price field should have @Column annotation");
        assertEquals("price", columnAnnotation.name(), 
            "price field should have correct column name");
        assertFalse(columnAnnotation.nullable(), 
            "price field should be marked as not nullable");
    }

    @Test
    void testMetadataFieldAnnotations() throws NoSuchFieldException {
        // Arrange
        Field metadataField = Item.class.getDeclaredField("metadata");

        // Act
        OneToMany oneToManyAnnotation = metadataField.getAnnotation(OneToMany.class);

        // Assert
        assertNotNull(oneToManyAnnotation, "metadata field should have @OneToMany annotation");
        assertEquals("item", oneToManyAnnotation.mappedBy(), 
            "metadata field should have correct mappedBy value");
    }
}