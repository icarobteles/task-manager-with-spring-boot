package com.example.taskmanager.entities;

import com.example.taskmanager.core.BaseEntity;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.enums.TaskStatusOperationFeedback;
import com.example.taskmanager.exceptions.tasks.IllegalTaskStatusUpdateException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

  @Setter
  @NotBlank(message = "O título da tarefa é obrigatório")
  @Size(min = 3, message = "O título da tarefa deve ter no mínimo 3 caracteres")
  @Size(max = 75, message = "O título da tarefa deve ter no máximo 75 caracteres")
  @Column(name = "title", nullable = false, unique = true, length = 75)
  private String title;

  @Setter
  @NotBlank(message = "A descrição da tarefa é obrigatória")
  @Size(min = 3, message = "A descrição da tarefa deve ter no mínimo 3 caracteres")
  @Column(name = "description", nullable = false)
  private String description;

  @NotNull(message = "O status da tarefa é obrigatório")
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TaskStatus status;

  @JsonIgnore
  @NotNull(message = "O autor da tarefa é obrigatório")
  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  public Task(String title, String description, User author) {
    this.title = title;
    this.description = description;
    this.author = author;
    this.status = TaskStatus.PENDING;
  }

  public void start() {
    switch (this.status) {
      case PENDING -> this.status = TaskStatus.IN_PROGRESS;
      case IN_PROGRESS -> throw new IllegalTaskStatusUpdateException(TaskStatusOperationFeedback.IS_ALREADY_IN_PROGRESS.getMessage());
      case DONE -> throw new IllegalTaskStatusUpdateException(TaskStatusOperationFeedback.HAS_ALREADY_BEEN_FINISHED.getMessage());
    }
  }

  public void complete() {
    switch (this.status) {
      case PENDING -> throw new IllegalTaskStatusUpdateException(TaskStatusOperationFeedback.HAS_NOT_YET_STARTED.getMessage());
      case IN_PROGRESS -> this.status = TaskStatus.DONE;
      case DONE -> throw new IllegalTaskStatusUpdateException(TaskStatusOperationFeedback.HAS_ALREADY_BEEN_FINISHED.getMessage());
    }
  }

  public void reset() {
    switch (this.status) {
      case PENDING -> throw new IllegalTaskStatusUpdateException(TaskStatusOperationFeedback.HAS_NOT_YET_STARTED.getMessage());
      case IN_PROGRESS -> this.status = TaskStatus.PENDING;
      case DONE -> this.status = TaskStatus.PENDING;
    }
  }

}
