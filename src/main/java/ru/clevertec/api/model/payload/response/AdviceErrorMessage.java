package ru.clevertec.api.model.payload.response;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.clevertec.api.model.payload.ServerResponse;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdviceErrorMessage implements ServerResponse {
  int statusCode;
  String message;
  String description;
  ZonedDateTime timestamp;

  public AdviceErrorMessage(int statusCode, String message, String description) {
    this.statusCode = statusCode;
    this.message = message;
    this.description = description;
    timestamp = ZonedDateTime.now();
  }
}
