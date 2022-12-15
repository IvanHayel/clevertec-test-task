package ru.clevertec.api.exception.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ReceiptNotFoundExceptionTest {
  private static final Long TEST_ID = 123L;
  private static final String TEST_MESSAGE = "Receipt with ID 123 not found!";
  private static final int SUPPRESSED_LENGTH = 0;

  /** Method under test: {@link ReceiptNotFoundException#ReceiptNotFoundException(Long)} */
  @Test
  void testConstructor() {
    var actualReceiptNotFoundException = new ReceiptNotFoundException(TEST_ID);
    assertNull(actualReceiptNotFoundException.getCause());
    assertEquals(SUPPRESSED_LENGTH, actualReceiptNotFoundException.getSuppressed().length);
    assertEquals(TEST_MESSAGE, actualReceiptNotFoundException.getMessage());
    assertEquals(TEST_MESSAGE, actualReceiptNotFoundException.getLocalizedMessage());
  }
}
