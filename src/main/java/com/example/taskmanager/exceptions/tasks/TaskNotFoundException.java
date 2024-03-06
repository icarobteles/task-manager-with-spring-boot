package com.example.taskmanager.exceptions.tasks;

import org.springframework.http.HttpStatus;

import com.example.taskmanager.core.HttpException;

public class TaskNotFoundException extends HttpException {

  public TaskNotFoundException() {
    super("Nenhuma tarefa foi encontrada", HttpStatus.NOT_FOUND);
  }
  
}
