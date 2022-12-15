package ru.clevertec.api.exception.receipt;

public class ReceiptNotFoundException extends RuntimeException {
  private static final String MESSAGE_WITH_ID = "Receipt with ID %d not found!";

  public ReceiptNotFoundException(Long id) {
    super(String.format(MESSAGE_WITH_ID, id));
  }
}
