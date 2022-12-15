package ru.clevertec.api.exception.receipt;

public class DiscountCardNotFoundException extends RuntimeException {
  private static final String MESSAGE_WITH_ID = "Discount card with ID %d not found!";

  public DiscountCardNotFoundException(Long id) {
    super(String.format(MESSAGE_WITH_ID, id));
  }
}
