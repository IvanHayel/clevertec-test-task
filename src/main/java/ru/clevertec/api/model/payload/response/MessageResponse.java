package ru.clevertec.api.model.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.clevertec.api.model.payload.ServerResponse;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageResponse implements ServerResponse {
  public static final MessageResponse SUCCESS_DELETE_PRODUCT = formSuccessDeleteResponse("Product");
  public static final MessageResponse SUCCESS_DELETE_DISCOUNT_CARD =
      formSuccessDeleteResponse("Discount card");
  public static final MessageResponse SUCCESS_DELETE_RECEIPT = formSuccessDeleteResponse("Receipt");

  @NonNull String message;

  public static MessageResponse formSuccessDeleteResponse(String name) {
    return new MessageResponse(String.format("%s deleted successfully!", name));
  }
}
