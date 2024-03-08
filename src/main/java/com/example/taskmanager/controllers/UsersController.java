package com.example.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dtos.users.UpdateUserDTO;
import com.example.taskmanager.entities.User;
import com.example.taskmanager.services.UsersService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
  private UsersService usersService;

  @GetMapping()
  public ResponseEntity<User> show() {
    User user = this.usersService.show();
    return ResponseEntity.ok(user);
  }

  @PatchMapping()
  public ResponseEntity<User> update(@RequestBody @Valid UpdateUserDTO body) {
    User user = this.usersService.update(body);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping()
  public ResponseEntity<Void> delete() {
    this.usersService.delete();
    return ResponseEntity.noContent().build();
  }

}
