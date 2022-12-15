package ru.clevertec.api.web.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;
import ru.clevertec.api.model.payload.response.ErrorMessage;

class GlobalControllerAdviceTest {
  private static final int TEST_STATUS_CODE = HttpStatus.BAD_REQUEST.value();
  private static final String TEST_DESCRIPTION = "uri=";
  private static final ServletWebRequest TEST_REQUEST;
  private static final MethodArgumentNotValidException TEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION;
  private static final NumberFormatException TEST_NUMBER_FORMAT_EXCEPTION;

  static {
    TEST_REQUEST = new ServletWebRequest(new MockHttpServletRequest());
    TEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION =
        new MethodArgumentNotValidException(null, new BindException("Target", "Object Name"));
    TEST_NUMBER_FORMAT_EXCEPTION = new NumberFormatException();
  }

  /**
   * Method under test: {@link
   * GlobalControllerAdvice#handleMethodArgumentNotValidException(WebRequest,
   * MethodArgumentNotValidException)}
   */
  @Test
  void testHandleMethodArgumentNotValidException() {
    var globalControllerAdvice = new GlobalControllerAdvice();
    assertEquals(
        TEST_DESCRIPTION,
        ((AdviceErrorMessage)
                globalControllerAdvice.handleMethodArgumentNotValidException(
                    TEST_REQUEST, TEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION))
            .getDescription());
    assertEquals(
        TEST_STATUS_CODE,
        ((AdviceErrorMessage)
                globalControllerAdvice.handleMethodArgumentNotValidException(
                    TEST_REQUEST, TEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION))
            .getStatusCode());
    assertEquals(
        ErrorMessage.INVALID_API_USAGE.getMessage(),
        ((AdviceErrorMessage)
                globalControllerAdvice.handleMethodArgumentNotValidException(
                    TEST_REQUEST, TEST_METHOD_ARGUMENT_NOT_VALID_EXCEPTION))
            .getMessage());
  }

  /**
   * Method under test: {@link GlobalControllerAdvice#handleNumberFormatException(WebRequest,
   * NumberFormatException)}
   */
  @Test
  void testHandleNumberFormatException() {
    GlobalControllerAdvice globalControllerAdvice = new GlobalControllerAdvice();
    ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest());
    assertEquals(
        "uri=",
        ((AdviceErrorMessage)
                globalControllerAdvice.handleNumberFormatException(
                    request, new NumberFormatException()))
            .getDescription());
    assertEquals(
        400,
        ((AdviceErrorMessage)
                globalControllerAdvice.handleNumberFormatException(
                    request, new NumberFormatException()))
            .getStatusCode());
    assertEquals(
        ErrorMessage.INVALID_API_USAGE.getMessage(),
        ((AdviceErrorMessage)
                globalControllerAdvice.handleNumberFormatException(
                    request, new NumberFormatException()))
            .getMessage());
  }
}
