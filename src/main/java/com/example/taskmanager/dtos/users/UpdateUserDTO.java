package com.example.taskmanager.dtos.users;

import java.util.Optional;

public class UpdateUserDTO {
  private final Optional<String> name;
  private final Optional<String> email;
  private final Optional<String> password;

  public UpdateUserDTO(String name, String email, String password) {
    this.name = this.parseOptional(name);
    this.email = this.parseOptional(email);
    this.password = this.parseOptional(password);
  }

  private <T extends Object> Optional<T> parseOptional(T value) {
    return value == null ? Optional.empty() : Optional.of(value);
  }

  public Optional<String> name() {
    return this.name;
  }

  public Optional<String> email() {
    return this.email;
  }

  public Optional<String> password() {
    return this.password;
  }
}
