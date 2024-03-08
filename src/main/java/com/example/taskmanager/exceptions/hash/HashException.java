package com.example.taskmanager.exceptions.hash;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class HashException extends HttpException {

  public HashException(String message, HttpStatus status) {
    super(message, status);
  }
  
}
