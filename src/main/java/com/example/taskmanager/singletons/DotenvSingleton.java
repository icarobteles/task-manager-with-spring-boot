package com.example.taskmanager.singletons;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class DotenvSingleton {
  private static Dotenv instance;

  public static Dotenv getInstance() {
    if (instance == null) {
      instance = Dotenv.load();
    }
    return instance;
  }

}
