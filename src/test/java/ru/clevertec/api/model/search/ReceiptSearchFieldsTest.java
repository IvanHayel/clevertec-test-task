package ru.clevertec.api.model.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReceiptSearchFieldsTest {
  private static final int TEST_FIELD_LENGTH = ProductSearchFields.values().length;
  private static final String TEST_FIELD_NAME = "description";
  private static final int TEST_INDEX = 0;

  /** Method under test: {@link ReceiptSearchFields#getAllFieldNames()} */
  @Test
  void testGetAllFieldNames() {
    var actualAllFieldNames = ReceiptSearchFields.getAllFieldNames();
    assertEquals(TEST_FIELD_LENGTH, actualAllFieldNames.length);
    assertEquals(TEST_FIELD_NAME, actualAllFieldNames[TEST_INDEX]);
  }
}
