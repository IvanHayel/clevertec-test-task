package ru.clevertec.api.web.controller.advice;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.clevertec.api.model.payload.ServerResponse;
import ru.clevertec.api.model.payload.response.AdviceErrorMessage;
import ru.clevertec.api.model.payload.response.ErrorMessage;

@RestControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handleMethodArgumentNotValidException(
      WebRequest request, MethodArgumentNotValidException exception) {
    var fieldError = exception.getBindingResult().getFieldError();
    var message =
        Objects.isNull(fieldError)
            ? ErrorMessage.INVALID_API_USAGE.getMessage()
            : fieldError.getDefaultMessage();
    var description = request.getDescription(false);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ServerResponse handleNumberFormatException(
      WebRequest request, NumberFormatException exception) {
    var message = ErrorMessage.INVALID_API_USAGE.getMessage();
    var description = request.getDescription(false);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }
}
