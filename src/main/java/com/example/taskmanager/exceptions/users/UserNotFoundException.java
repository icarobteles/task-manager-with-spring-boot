package com.example.taskmanager.exceptions.users;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class UserNotFoundException extends HttpException {
  public UserNotFoundException() {
    super("Nenhum usuário foi encontrado", HttpStatus.NOT_FOUND);
  }
}
