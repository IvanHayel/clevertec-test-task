package ru.clevertec.api.exception.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DiscountCardNotFoundExceptionTest {
  private static final Long TEST_ID = 123L;
  private static final String TEST_MESSAGE = "Discount card with ID 123 not found!";
  private static final int SUPPRESSED_LENGTH = 0;

  /**
   * Method under test: {@link DiscountCardNotFoundException#DiscountCardNotFoundException(Long)}
   */
  @Test
  void testConstructor() {
    var actualDiscountCardNotFoundException = new DiscountCardNotFoundException(TEST_ID);
    assertNull(actualDiscountCardNotFoundException.getCause());
    assertEquals(SUPPRESSED_LENGTH, actualDiscountCardNotFoundException.getSuppressed().length);
    assertEquals(TEST_MESSAGE, actualDiscountCardNotFoundException.getMessage());
    assertEquals(TEST_MESSAGE, actualDiscountCardNotFoundException.getLocalizedMessage());
  }
}
