package ru.clevertec.api.web.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.exception.receipt.ProductNotFoundException;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;

class ProductControllerAdviceTest {
  private static final Long TEST_ID = 123L;
  private static final int TEST_STATUS_CODE = HttpStatus.BAD_REQUEST.value();
  private static final String TEST_MESSAGE = "Product with ID 123 not found!";
  private static final String TEST_DESCRIPTION = "uri=";
  private static final ProductNotFoundException TEST_EXCEPTION;
  private static final ServletWebRequest TEST_REQUEST;

  static {
    TEST_EXCEPTION = new ProductNotFoundException(TEST_ID);
    TEST_REQUEST = new ServletWebRequest(new MockHttpServletRequest());
  }

  /**
   * Method under test: {@link ProductControllerAdvice#handleProductNotFoundException(WebRequest,
   * ProductNotFoundException)}
   */
  @Test
  void testHandleProductNotFoundException() {
    var productControllerAdvice = new ProductControllerAdvice();
    assertEquals(
        TEST_DESCRIPTION,
        ((AdviceErrorMessage)
                productControllerAdvice.handleProductNotFoundException(
                    TEST_REQUEST, TEST_EXCEPTION))
            .getDescription());
    assertEquals(
        TEST_STATUS_CODE,
        ((AdviceErrorMessage)
                productControllerAdvice.handleProductNotFoundException(
                    TEST_REQUEST, TEST_EXCEPTION))
            .getStatusCode());
    assertEquals(
        TEST_MESSAGE,
        ((AdviceErrorMessage)
                productControllerAdvice.handleProductNotFoundException(
                    TEST_REQUEST, TEST_EXCEPTION))
            .getMessage());
  }
}
