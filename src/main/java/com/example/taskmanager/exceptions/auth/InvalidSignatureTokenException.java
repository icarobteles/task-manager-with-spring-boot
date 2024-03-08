package com.example.taskmanager.exceptions.auth;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class InvalidSignatureTokenException extends HttpException {
  public InvalidSignatureTokenException() {
    super("A assinatura do token é inválida", HttpStatus.UNAUTHORIZED);
  }
}
