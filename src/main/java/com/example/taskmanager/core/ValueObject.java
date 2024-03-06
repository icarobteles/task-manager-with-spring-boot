package com.example.taskmanager.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "value")
@AllArgsConstructor
public abstract class ValueObject<T> {
  private final T value;
}
