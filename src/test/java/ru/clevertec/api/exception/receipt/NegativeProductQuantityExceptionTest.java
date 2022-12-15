package ru.clevertec.api.exception.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NegativeProductQuantityExceptionTest {
  private static final String TEST_MESSAGE = "Product quantity cannot be negative!";
  private static final int SUPPRESSED_LENGTH = 0;

  /**
   * Method under test: default or parameterless constructor of {@link
   * NegativeProductQuantityException}
   */
  @Test
  void testConstructor() {
    var actualNegativeProductQuantityException = new NegativeProductQuantityException();
    assertNull(actualNegativeProductQuantityException.getCause());
    assertEquals(SUPPRESSED_LENGTH, actualNegativeProductQuantityException.getSuppressed().length);
    assertEquals(TEST_MESSAGE, actualNegativeProductQuantityException.getMessage());
    assertEquals(TEST_MESSAGE, actualNegativeProductQuantityException.getLocalizedMessage());
  }
}
