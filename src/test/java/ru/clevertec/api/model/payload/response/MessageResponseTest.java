package ru.clevertec.api.model.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessageResponseTest {
  private static final String TEST_NAME = "Receipt";
  private static final String TEST_MESSAGE = "Receipt deleted successfully!";

  /** Method under test: {@link MessageResponse#formSuccessDeleteResponse(String)} */
  @Test
  void testFormSuccessDeleteResponse() {
    assertEquals(TEST_MESSAGE, MessageResponse.formSuccessDeleteResponse(TEST_NAME).getMessage());
  }
}
