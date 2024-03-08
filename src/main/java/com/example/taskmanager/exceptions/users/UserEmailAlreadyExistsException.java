package com.example.taskmanager.exceptions.users;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class UserEmailAlreadyExistsException extends HttpException {
  public UserEmailAlreadyExistsException() {
    super("Email já cadastrado", HttpStatus.CONFLICT);
  }
}
