package com.example.taskmanager.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TaskStatus {
  PENDING("Pendente"),
  IN_PROGRESS("Em andamento"),
  DONE("Concluída");

  private String value;

  @JsonValue
  public String getValue() {
    return this.value;
  }

  public static TaskStatus fromValue(String value) {
    for (TaskStatus status : TaskStatus.values()) {
      if (status.getValue().equals(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException(TaskStatusOperationFeedback.INVALID_STATUS.getMessage());
  }
}
