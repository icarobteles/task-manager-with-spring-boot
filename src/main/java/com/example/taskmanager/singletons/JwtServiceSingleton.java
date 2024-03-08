package com.example.taskmanager.singletons;

import org.springframework.stereotype.Component;

import com.example.taskmanager.services.JwtService;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class JwtServiceSingleton {
  private static JwtService instance;

  public static JwtService getInstance() {
    if (instance == null) {
      instance = new JwtService();
    }
    return instance;
  }

}
