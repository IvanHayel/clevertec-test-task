package ru.clevertec.api.model.search;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ReceiptSearchFields {
  DESCRIPTION("description");

  String fieldName;

  public static String[] getAllFieldNames() {
    return Arrays.stream(ReceiptSearchFields.values())
        .map(ReceiptSearchFields::getFieldName)
        .toArray(String[]::new);
  }
}
