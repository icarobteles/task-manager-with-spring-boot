package com.example.taskmanager.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.taskmanager.core.HttpException;
import com.example.taskmanager.core.HttpExceptionResponse;

@RestControllerAdvice
public class HttpExceptionHandler {

  @ExceptionHandler(HttpException.class)
  public ResponseEntity<HttpExceptionResponse> handleHttpException(HttpException e) {
    HttpExceptionResponse response = new HttpExceptionResponse(e);
    e.printStackTrace();
    return ResponseEntity.status(e.getStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<HttpExceptionResponse> handleConstraintViolationException(MethodArgumentNotValidException e) {
    var primaryViolation = e.getBindingResult().getFieldErrors().stream().findFirst();
    
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Ocorreu um erro inesperado no servidor";

    if (primaryViolation.isPresent()) {
      status = HttpStatus.UNPROCESSABLE_ENTITY;
      message = primaryViolation.get().getDefaultMessage();
    }

    HttpException exception = new HttpException(message, status);
    HttpExceptionResponse response = new HttpExceptionResponse(exception);
    e.printStackTrace();
    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpExceptionResponse> handleException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Ocorreu um erro inesperado no servidor";
    HttpException exception = new HttpException(message, status);
    HttpExceptionResponse response = new HttpExceptionResponse(exception);
    e.printStackTrace();
    return ResponseEntity.status(status).body(response);
  }
  
}
