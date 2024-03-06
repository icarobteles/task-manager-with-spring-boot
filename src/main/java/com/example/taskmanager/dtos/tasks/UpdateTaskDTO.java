package com.example.taskmanager.dtos.tasks;

import java.util.Optional;

public class UpdateTaskDTO {

  private final Optional<String> title;
  private final Optional<String> description;

  public UpdateTaskDTO(String title, String description) {
    this.title = this.parseOptional(title);
    this.description = this.parseOptional(description);
  }

  private <T extends Object> Optional<T> parseOptional(T value) {
    return value == null ? Optional.empty() : Optional.of(value);
  }

  public Optional<String> title() {
    return this.title;
  }

  public Optional<String> description() {
    return this.description;
  }
}
