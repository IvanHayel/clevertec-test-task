package ru.clevertec.api.model.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
  INVALID_API_USAGE(
      "Please visit https://github.com/IvanHayel/clevertec-test-task to see API features.");

  String message;
}
