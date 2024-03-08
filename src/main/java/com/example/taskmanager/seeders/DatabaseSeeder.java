package com.example.taskmanager.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.taskmanager.entities.Task;
import com.example.taskmanager.entities.User;
import com.example.taskmanager.repositories.TasksRepository;
import com.example.taskmanager.repositories.UsersRepository;

import java.util.Arrays;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@Profile("test")
public class DatabaseSeeder implements CommandLineRunner {

  private final UsersRepository usersRepository;
  private final TasksRepository tasksRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void run(String... args) throws Exception {

    var user1 = new User("John Doe", "johndoe@gmail.com", "johnDOE123$");
    var user2 = new User("Jane Doe", "janedoe@gmail.com", "janeDOE123$");
    this.usersRepository.saveAll(Arrays.asList(user1, user2));

    var task1 = new Task("Task 1", "Description 1", user1);
    var task2 = new Task("Task 2", "Description 2", user1);
    var task3 = new Task("Task 3", "Description 3", user2);
    this.tasksRepository.saveAll(Arrays.asList(task1, task2, task3));
  }

}
