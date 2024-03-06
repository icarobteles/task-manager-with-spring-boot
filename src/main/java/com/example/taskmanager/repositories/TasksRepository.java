package com.example.taskmanager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.entities.Task;

public interface TasksRepository extends JpaRepository<Task, UUID> {

}

