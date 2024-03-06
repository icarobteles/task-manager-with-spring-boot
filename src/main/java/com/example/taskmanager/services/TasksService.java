package com.example.taskmanager.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dtos.tasks.CreateTaskDTO;
import com.example.taskmanager.dtos.tasks.UpdateTaskDTO;
import com.example.taskmanager.entities.Task;
import com.example.taskmanager.exceptions.tasks.TaskNotFoundException;
import com.example.taskmanager.repositories.TasksRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TasksService {
  private final TasksRepository tasksRepository;

  public Task create(CreateTaskDTO data) {

    String title = data.title();
    String description = data.description();
    
    Task task = new Task(title, description);

    return this.tasksRepository.save(task);
  }

  public Task update(UUID id, UpdateTaskDTO data) {

    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);

    data.title().ifPresent(task::setTitle);
    data.description().ifPresent(task::setDescription);

    return this.tasksRepository.save(task);
  }

  public List<Task> list() {
    return this.tasksRepository.findAll();
  }

  public Task show(UUID id) {
    return this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
  }

  public void delete(UUID id) {
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    this.tasksRepository.delete(task);
  }

  public Task complete(UUID id) {
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    task.complete();
    return this.tasksRepository.save(task);
  }

  public Task start(UUID id) {
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    task.start();
    return this.tasksRepository.save(task);
  }

  public Task reset(UUID id) {
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    task.reset();
    return this.tasksRepository.save(task);
  }
}
