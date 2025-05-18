package com.back_puyoReboot.Exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGlobalException(Exception ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur serveur est survenue");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex
      .getBindingResult()
      .getFieldErrors()
      .forEach(error -> {
        String fieldName = error.getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
