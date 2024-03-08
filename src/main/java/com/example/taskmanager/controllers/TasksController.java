package com.example.taskmanager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dtos.tasks.CreateTaskDTO;
import com.example.taskmanager.dtos.tasks.UpdateTaskDTO;
import com.example.taskmanager.entities.Task;
import com.example.taskmanager.services.TasksService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/tasks")
public class TasksController {
  private final TasksService tasksService;

  @GetMapping() 
  public ResponseEntity<List<Task>> index() {
    List<Task> tasks = this.tasksService.list();
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> show(@PathVariable UUID id) {
    Task task = this.tasksService.show(id);
    return ResponseEntity.ok(task);
  }

  @PostMapping()
  public ResponseEntity<Task> create(@RequestBody @Valid CreateTaskDTO body) {
    Task task = this.tasksService.create(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Task> update(@PathVariable UUID id, @RequestBody @Valid UpdateTaskDTO body) {
    Task task = this.tasksService.update(id, body);
    return ResponseEntity.ok(task);
  }

  @PatchMapping("/{id}/complete")
  public ResponseEntity<Task> complete(@PathVariable UUID id) {
    Task task = this.tasksService.complete(id);
    return ResponseEntity.ok(task);
  }

  @PatchMapping("/{id}/start")
  public ResponseEntity<Task> start(@PathVariable UUID id) {
    Task task = this.tasksService.start(id);
    return ResponseEntity.ok(task);
  }

  @PatchMapping("/{id}/reset")
  public ResponseEntity<Task> reset(@PathVariable UUID id) {
    Task task = this.tasksService.reset(id);
    return ResponseEntity.ok(task);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    this.tasksService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
