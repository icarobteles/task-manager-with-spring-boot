package com.example.taskmanager.exceptions.tasks;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class IllegalTaskStatusUpdateException extends HttpException {
  public IllegalTaskStatusUpdateException(String message) {
    super(message, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
