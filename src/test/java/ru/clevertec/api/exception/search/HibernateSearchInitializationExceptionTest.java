package ru.clevertec.api.exception.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class HibernateSearchInitializationExceptionTest {
  private static final String TEST_MESSAGE = "Unable to initialize hibernate search!";
  private static final int SUPPRESSED_LENGTH = 0;

  /**
   * Method under test: default or parameterless constructor of {@link
   * HibernateSearchInitializationException}
   */
  @Test
  void testConstructor() {
    var actualHibernateSearchInitializationException = new HibernateSearchInitializationException();
    assertNull(actualHibernateSearchInitializationException.getCause());
    assertEquals(
        SUPPRESSED_LENGTH, actualHibernateSearchInitializationException.getSuppressed().length);
    assertEquals(TEST_MESSAGE, actualHibernateSearchInitializationException.getMessage());
    assertEquals(TEST_MESSAGE, actualHibernateSearchInitializationException.getLocalizedMessage());
  }
}
