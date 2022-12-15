package ru.clevertec.api.web.controller.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.exception.receipt.NegativeProductQuantityException;
import ru.clevertec.api.exception.receipt.ReceiptNotFoundException;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;

class ReceiptControllerAdviceTest {
  private static final Long TEST_ID = 123L;
  private static final int TEST_STATUS_CODE = HttpStatus.BAD_REQUEST.value();
  private static final String TEST_DESCRIPTION = "uri=";
  private static final String TEST_RECEIPT_NOT_FOUND_MESSAGE = "Receipt with ID 123 not found!";
  private static final String TEST_NEGATIVE_PRODUCT_QUANTITY_MESSAGE;
  private static final ServletWebRequest TEST_REQUEST;
  private static final ReceiptNotFoundException TEST_RECEIPT_NOT_FOUND_EXCEPTION;
  private static final NegativeProductQuantityException TEST_NEGATIVE_PRODUCT_QUANTITY_EXCEPTION;

  static {
    TEST_RECEIPT_NOT_FOUND_EXCEPTION = new ReceiptNotFoundException(TEST_ID);
    TEST_NEGATIVE_PRODUCT_QUANTITY_EXCEPTION = new NegativeProductQuantityException();
    TEST_REQUEST = new ServletWebRequest(new MockHttpServletRequest());
    TEST_NEGATIVE_PRODUCT_QUANTITY_MESSAGE = "Product quantity cannot be negative!";
  }

  /**
   * Method under test: {@link ReceiptControllerAdvice#handleReceiptNotFoundException(WebRequest,
   * ReceiptNotFoundException)}
   */
  @Test
  void testHandleReceiptNotFoundException() {
    var receiptControllerAdvice = new ReceiptControllerAdvice();
    assertEquals(
        TEST_DESCRIPTION,
        ((AdviceErrorMessage)
                receiptControllerAdvice.handleReceiptNotFoundException(
                    TEST_REQUEST, TEST_RECEIPT_NOT_FOUND_EXCEPTION))
            .getDescription());
    assertEquals(
        TEST_STATUS_CODE,
        ((AdviceErrorMessage)
                receiptControllerAdvice.handleReceiptNotFoundException(
                    TEST_REQUEST, TEST_RECEIPT_NOT_FOUND_EXCEPTION))
            .getStatusCode());
    assertEquals(
        TEST_RECEIPT_NOT_FOUND_MESSAGE,
        ((AdviceErrorMessage)
                receiptControllerAdvice.handleReceiptNotFoundException(
                    TEST_REQUEST, TEST_RECEIPT_NOT_FOUND_EXCEPTION))
            .getMessage());
  }

  /**
   * Method under test: {@link
   * ReceiptControllerAdvice#handleNegativeProductQuantityException(WebRequest,
   * NegativeProductQuantityException)}
   */
  @Test
  void testHandleNegativeProductQuantityException() {
    var receiptControllerAdvice = new ReceiptControllerAdvice();
    assertEquals(
        TEST_DESCRIPTION,
        ((AdviceErrorMessage)
                receiptControllerAdvice.handleNegativeProductQuantityException(
                    TEST_REQUEST, TEST_NEGATIVE_PRODUCT_QUANTITY_EXCEPTION))
            .getDescription());
    assertEquals(
        TEST_STATUS_CODE,
        ((AdviceErrorMessage)
                receiptControllerAdvice.handleNegativeProductQuantityException(
                    TEST_REQUEST, TEST_NEGATIVE_PRODUCT_QUANTITY_EXCEPTION))
            .getStatusCode());
    assertEquals(
        TEST_NEGATIVE_PRODUCT_QUANTITY_MESSAGE,
        ((AdviceErrorMessage)
                receiptControllerAdvice.handleNegativeProductQuantityException(
                    TEST_REQUEST, TEST_NEGATIVE_PRODUCT_QUANTITY_EXCEPTION))
            .getMessage());
  }
}
