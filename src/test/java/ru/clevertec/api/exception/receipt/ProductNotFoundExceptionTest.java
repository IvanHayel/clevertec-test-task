package ru.clevertec.api.exception.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ProductNotFoundExceptionTest {
  private static final Long TEST_ID = 123L;
  private static final String TEST_MESSAGE = "Product with ID 123 not found!";
  private static final int SUPPRESSED_LENGTH = 0;

  /** Method under test: {@link ProductNotFoundException#ProductNotFoundException(Long)} */
  @Test
  void testConstructor() {
    var actualProductNotFoundException = new ProductNotFoundException(TEST_ID);
    assertNull(actualProductNotFoundException.getCause());
    assertEquals(SUPPRESSED_LENGTH, actualProductNotFoundException.getSuppressed().length);
    assertEquals(TEST_MESSAGE, actualProductNotFoundException.getMessage());
    assertEquals(TEST_MESSAGE, actualProductNotFoundException.getLocalizedMessage());
  }
}
