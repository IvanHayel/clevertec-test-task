package ru.clevertec.api.exception.receipt;

public class ProductNotFoundException extends RuntimeException {
  private static final String MESSAGE_WITH_ID = "Product with ID %d not found!";

  public ProductNotFoundException(Long id) {
    super(String.format(MESSAGE_WITH_ID, id));
  }
}
