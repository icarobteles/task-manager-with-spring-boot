package com.example.taskmanager.dtos.auth;

public record AuthResponseDTO(

  String accessToken,
  String refreshToken

) {}
