package com.example.taskmanager.exceptions.auth;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class InvalidJwtTokenException extends HttpException {
  public InvalidJwtTokenException() {
    super("O token JWT deve ser válido", HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
