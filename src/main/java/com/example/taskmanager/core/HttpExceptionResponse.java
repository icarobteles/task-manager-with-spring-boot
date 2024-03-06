package com.example.taskmanager.core;

import java.time.Instant;

public record HttpExceptionResponse(
  boolean error,
  String message,
  int status,
  Instant timestamp
) {
  public HttpExceptionResponse(HttpException e) {
    this(true, e.getMessage(), e.getStatus().value(), e.getTimestamp());
  }
}
