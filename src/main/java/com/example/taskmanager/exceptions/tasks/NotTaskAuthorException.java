package com.example.taskmanager.exceptions.tasks;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class NotTaskAuthorException extends HttpException {
  public NotTaskAuthorException() {
    super("Apenas o autor da tarefa pode realizar esta ação", HttpStatus.UNAUTHORIZED);
  }
}
