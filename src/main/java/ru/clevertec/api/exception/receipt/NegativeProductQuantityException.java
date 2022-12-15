package ru.clevertec.api.exception.receipt;

public class NegativeProductQuantityException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "Product quantity cannot be negative!";

  public NegativeProductQuantityException() {
    super(DEFAULT_MESSAGE);
  }
}
