package com.example.taskmanager.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshDTO(

  @NotBlank(message = "O token de atualização é obrigatório")
  String refreshToken

) {}
