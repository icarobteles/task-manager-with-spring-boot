package com.example.taskmanager.exceptions.auth;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class ExpiredJwtTokenException extends HttpException {
  public ExpiredJwtTokenException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}
