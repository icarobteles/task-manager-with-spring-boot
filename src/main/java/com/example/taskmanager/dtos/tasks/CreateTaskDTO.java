package com.example.taskmanager.dtos.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskDTO(

  @NotBlank(message = "O título da tarefa é obrigatório")
  @Size(min = 3, message = "O título da tarefa deve ter no mínimo 3 caracteres")
  @Size(max = 75, message = "O título da tarefa deve ter no máximo 75 caracteres")
  String title,

  @NotBlank(message = "A descrição da tarefa é obrigatória")
  @Size(min = 3, message = "A descrição da tarefa deve ter no mínimo 3 caracteres")
  String description
) {}
