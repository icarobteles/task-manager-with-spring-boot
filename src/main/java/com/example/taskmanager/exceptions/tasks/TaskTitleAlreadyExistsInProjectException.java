package com.example.taskmanager.exceptions.tasks;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class TaskTitleAlreadyExistsInProjectException extends HttpException {
  public TaskTitleAlreadyExistsInProjectException() {
    super("O título da tarefa deve ser único em um projeto", HttpStatus.CONFLICT);
  }
}
