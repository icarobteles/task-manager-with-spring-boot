package com.example.taskmanager.dtos.auth;

import com.example.taskmanager.annotations.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

  @NotBlank(message = "O email é obrigatório")
  @Email(message = "O email deve ser válido")
  String email,

  @NotBlank(message = "A senha é obrigatória")
  @StrongPassword
  String password

) {}
