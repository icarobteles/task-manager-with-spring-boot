package com.example.taskmanager.core;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
  private final HttpStatus status;
  private final Instant timestamp;

  public HttpException(String message, HttpStatus status) {
    super(message);
    this.status = status;
    this.timestamp = Instant.now();
  }

  public int getStatusCode() {
    return this.status.value();
  }
}
