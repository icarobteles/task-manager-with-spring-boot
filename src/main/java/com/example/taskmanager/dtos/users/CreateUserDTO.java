package com.example.taskmanager.dtos.users;

import com.example.taskmanager.annotations.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
  @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
  String name,

  @NotBlank(message = "O email é obrigatório")
  @Email(message = "O email deve ser válido")
  String email,

  @NotBlank(message = "A senha é obrigatória")
  @StrongPassword
  String password

) {}
