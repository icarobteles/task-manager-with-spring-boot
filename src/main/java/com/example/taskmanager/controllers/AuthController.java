package com.example.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dtos.auth.AuthResponseDTO;
import com.example.taskmanager.dtos.auth.LoginDTO;
import com.example.taskmanager.dtos.auth.RefreshDTO;
import com.example.taskmanager.dtos.auth.RegisterDTO;
import com.example.taskmanager.services.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO body) {
    AuthResponseDTO response = this.authService.login(body);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterDTO body) {
    AuthResponseDTO response = this.authService.register(body);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponseDTO> refresh(@RequestBody @Valid RefreshDTO body) {
    AuthResponseDTO response = this.authService.refresh(body);
    return ResponseEntity.ok(response);
  }
}
