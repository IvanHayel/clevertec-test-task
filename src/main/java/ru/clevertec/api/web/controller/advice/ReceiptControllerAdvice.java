package ru.clevertec.api.web.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.exception.receipt.NegativeProductQuantityException;
import ru.clevertec.api.exception.receipt.ReceiptNotFoundException;
import ru.clevertec.api.model.payload.ServerResponse;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;

@RestControllerAdvice
public class ReceiptControllerAdvice {
  @ExceptionHandler(ReceiptNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handleReceiptNotFoundException(
      WebRequest request, ReceiptNotFoundException exception) {
    var message = exception.getMessage();
    var description = request.getDescription(false);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }

  @ExceptionHandler(NegativeProductQuantityException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handleNegativeProductQuantityException(
      WebRequest request, NegativeProductQuantityException exception) {
    var message = exception.getMessage();
    var description = request.getDescription(false);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }
}
