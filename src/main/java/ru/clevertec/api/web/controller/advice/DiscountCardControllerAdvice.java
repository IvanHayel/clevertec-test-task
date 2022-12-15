package ru.clevertec.api.web.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.exception.receipt.DiscountCardNotFoundException;
import ru.clevertec.api.model.payload.ServerResponse;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;

@RestControllerAdvice
public class DiscountCardControllerAdvice {
  @ExceptionHandler(DiscountCardNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handleDiscountCardNotFoundException(
      WebRequest request, DiscountCardNotFoundException exception) {
    var message = exception.getMessage();
    var description = request.getDescription(false);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }
}
