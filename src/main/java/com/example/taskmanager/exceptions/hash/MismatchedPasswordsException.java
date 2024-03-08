package com.example.taskmanager.exceptions.hash;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class MismatchedPasswordsException extends HttpException {

  public MismatchedPasswordsException() {
    super("As senhas não correspondem", HttpStatus.FORBIDDEN);
  }
  
}
