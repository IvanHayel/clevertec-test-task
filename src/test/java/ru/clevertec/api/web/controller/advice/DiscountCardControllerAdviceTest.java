package ru.clevertec.api.web.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.exception.receipt.DiscountCardNotFoundException;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;

class DiscountCardControllerAdviceTest {
  private static final Long TEST_ID = 123L;
  private static final int TEST_STATUS_CODE = HttpStatus.BAD_REQUEST.value();
  private static final String TEST_MESSAGE = "Discount card with ID 123 not found!";
  private static final String TEST_DESCRIPTION = "uri=";
  private static final DiscountCardNotFoundException TEST_EXCEPTION;
  private static final ServletWebRequest TEST_REQUEST;

  static {
    TEST_EXCEPTION = new DiscountCardNotFoundException(TEST_ID);
    TEST_REQUEST = new ServletWebRequest(new MockHttpServletRequest());
  }

  /**
   * Method under test: {@link
   * DiscountCardControllerAdvice#handleDiscountCardNotFoundException(WebRequest,
   * DiscountCardNotFoundException)}
   */
  @Test
  void testHandleDiscountCardNotFoundException() {
    var discountCardControllerAdvice = new DiscountCardControllerAdvice();
    assertEquals(
        TEST_DESCRIPTION,
        ((AdviceErrorMessage)
                discountCardControllerAdvice.handleDiscountCardNotFoundException(
                    TEST_REQUEST, TEST_EXCEPTION))
            .getDescription());
    assertEquals(
        TEST_STATUS_CODE,
        ((AdviceErrorMessage)
                discountCardControllerAdvice.handleDiscountCardNotFoundException(
                    TEST_REQUEST, TEST_EXCEPTION))
            .getStatusCode());
    assertEquals(
        TEST_MESSAGE,
        ((AdviceErrorMessage)
                discountCardControllerAdvice.handleDiscountCardNotFoundException(
                    TEST_REQUEST, TEST_EXCEPTION))
            .getMessage());
  }
}
