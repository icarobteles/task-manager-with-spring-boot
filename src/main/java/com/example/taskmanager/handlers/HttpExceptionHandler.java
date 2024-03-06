package com.example.taskmanager.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.taskmanager.core.HttpException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class HttpExceptionHandler {

  @ExceptionHandler(HttpException.class)
  public ResponseEntity<HttpException> handleHttpException(HttpException e) {
    return ResponseEntity.status(e.getStatus()).body(e);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<HttpException> handleConstraintViolationException(ConstraintViolationException e) {
    var primaryViolation = e.getConstraintViolations().stream().findFirst();
    
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Ocorreu um erro inesperado no servidor";

    if (primaryViolation.isPresent()) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
      message = primaryViolation.get().getMessage();
    }

    HttpException exception = new HttpException(message, status);
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpException> handleException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Ocorreu um erro inesperado no servidor";
    HttpException exception = new HttpException(message, status);
    return ResponseEntity.status(status).body(exception);
  }
  
}
