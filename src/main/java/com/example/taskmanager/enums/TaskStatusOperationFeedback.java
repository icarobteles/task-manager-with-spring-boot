package com.example.taskmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatusOperationFeedback {
  IS_ALREADY_IN_PROGRESS("A tarefa já está em andamento."),
  HAS_ALREADY_BEEN_FINISHED("A tarefa já foi concluída."),
  HAS_NOT_YET_STARTED("A tarefa ainda não foi iniciada."),
  INVALID_STATUS("Status inválido.");

  private final String message;
}
