package com.example.taskmanager.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.entities.Task;
import com.example.taskmanager.entities.User;

public interface TasksRepository extends JpaRepository<Task, UUID> {
  List<Task> findByAuthor(User author);
}

