package unit.com.github.psbds.errors.exceptions;

import com.github.psbds.errors.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    private static final String TEST_ERROR_CODE = "BUSINESS_ERROR_001";
    private static final String TEST_ERROR_MESSAGE = "A business rule violation occurred";
    
    @Test
    void constructor_when_validParametersProvided_should_setAllPropertiesCorrectly() {
        // Arrange
        String errorCode = TEST_ERROR_CODE;
        String errorMessage = TEST_ERROR_MESSAGE;

        // Act
        BusinessException exception = new BusinessException(errorCode, errorMessage);

        // Assert
        assertEquals(errorCode, exception.getErrorCode(), "Error code should be set correctly");
        assertEquals(errorMessage, exception.getErrorMessage(), "Error message should be set correctly");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should be set correctly");
        assertNull(exception.getCause(), "Cause should be null when not provided");
    }

    @Test
    void getErrorCode_when_called_should_returnErrorCodeSetInConstructor() {
        // Arrange
        BusinessException exception = new BusinessException(TEST_ERROR_CODE, TEST_ERROR_MESSAGE);

        // Act
        String result = exception.getErrorCode();

        // Assert
        assertEquals(TEST_ERROR_CODE, result, "getErrorCode should return the error code set in constructor");
    }

    @Test
    void getErrorMessage_when_called_should_returnErrorMessageSetInConstructor() {
        // Arrange
        BusinessException exception = new BusinessException(TEST_ERROR_CODE, TEST_ERROR_MESSAGE);

        // Act
        String result = exception.getErrorMessage();

        // Assert
        assertEquals(TEST_ERROR_MESSAGE, result, "getErrorMessage should return the error message set in constructor");
    }

    @Test
    void businessException_when_created_should_extendRuntimeException() {
        // Arrange
        BusinessException exception = new BusinessException(TEST_ERROR_CODE, TEST_ERROR_MESSAGE);

        // Act & Assert
        assertTrue(exception instanceof RuntimeException, "BusinessException should extend RuntimeException");
        assertTrue(exception instanceof Exception, "BusinessException should be an Exception");
        assertTrue(exception instanceof Throwable, "BusinessException should be a Throwable");
    }
}