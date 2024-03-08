package com.example.taskmanager.exceptions.auth;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class UserNotAuthenticatedException extends HttpException {
  public UserNotAuthenticatedException() {
    super("Usuário não autenticado", HttpStatus.UNAUTHORIZED);
  }
}
