package com.example.taskmanager.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.example.taskmanager.dtos.tasks.CreateTaskDTO;
import com.example.taskmanager.dtos.tasks.UpdateTaskDTO;
import com.example.taskmanager.entities.Task;
import com.example.taskmanager.entities.User;
import com.example.taskmanager.exceptions.auth.UserNotAuthenticatedException;
import com.example.taskmanager.exceptions.tasks.NotTaskAuthorException;
import com.example.taskmanager.exceptions.tasks.TaskNotFoundException;
import com.example.taskmanager.repositories.TasksRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TasksService {
  private final TasksRepository tasksRepository;

  private User getAuthenticatedUser() {
    User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_REQUEST);

    if (user == null) {
      throw new UserNotAuthenticatedException();
    }

    return user;
  }

  private void checkTaskAuthor(User author, Task task) {
    if (!task.getAuthor().getId().equals(author.getId())) {
      throw new NotTaskAuthorException();
    }
  }

  public Task create(CreateTaskDTO data) {

    String title = data.title();
    String description = data.description();

    User author = this.getAuthenticatedUser();
    
    Task task = new Task(title, description, author);

    return this.tasksRepository.save(task);
  }

  public Task update(UUID id, UpdateTaskDTO data) {

    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    User author = this.getAuthenticatedUser();
    this.checkTaskAuthor(author, task);

    data.title().ifPresent(task::setTitle);
    data.description().ifPresent(task::setDescription);

    return this.tasksRepository.save(task);
  }

  public List<Task> list() {
    User author = this.getAuthenticatedUser();
    return this.tasksRepository.findByAuthor(author);
  }

  public Task show(UUID id) {
    User author = this.getAuthenticatedUser();
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    this.checkTaskAuthor(author, task);
    return task;
  }

  public void delete(UUID id) {
    User author = this.getAuthenticatedUser();
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    this.checkTaskAuthor(author, task);
    this.tasksRepository.delete(task);
  }

  public Task complete(UUID id) {
    User author = this.getAuthenticatedUser();
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    this.checkTaskAuthor(author, task);
    task.complete();
    return this.tasksRepository.save(task);
  }

  public Task start(UUID id) {
    User author = this.getAuthenticatedUser();
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    this.checkTaskAuthor(author, task);
    task.start();
    return this.tasksRepository.save(task);
  }

  public Task reset(UUID id) {
    User author = this.getAuthenticatedUser();
    Task task = this.tasksRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    this.checkTaskAuthor(author, task);
    task.reset();
    return this.tasksRepository.save(task);
  }
}
