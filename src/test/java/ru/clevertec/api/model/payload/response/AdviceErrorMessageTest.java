package ru.clevertec.api.model.payload.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AdviceErrorMessageTest {
  private static final int TEST_STATUS_CODE = HttpStatus.BAD_REQUEST.value();
  private static final String TEST_MESSAGE = "Not all who wander are lost";
  private static final String TEST_DESCRIPTION = "The characteristics of someone or something";

  /** Method under test: {@link AdviceErrorMessage#AdviceErrorMessage(int, String, String)} */
  @Test
  void testConstructor() {
    var actualAdviceErrorMessage =
        new AdviceErrorMessage(TEST_STATUS_CODE, TEST_MESSAGE, TEST_DESCRIPTION);
    assertEquals(TEST_STATUS_CODE, actualAdviceErrorMessage.getStatusCode());
    assertEquals(TEST_MESSAGE, actualAdviceErrorMessage.getMessage());
    assertEquals(TEST_DESCRIPTION, actualAdviceErrorMessage.getDescription());
  }
}
