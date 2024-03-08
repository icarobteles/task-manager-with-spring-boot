package com.example.taskmanager.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.example.taskmanager.dtos.users.CreateUserDTO;
import com.example.taskmanager.dtos.users.UpdateUserDTO;
import com.example.taskmanager.entities.User;
import com.example.taskmanager.exceptions.auth.UserNotAuthenticatedException;
import com.example.taskmanager.exceptions.users.UserEmailAlreadyExistsException;
import com.example.taskmanager.repositories.UsersRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsersService {

  private UsersRepository usersRepository;

  private User getAuthenticatedUser() {
    User user = (User) RequestContextHolder.currentRequestAttributes().getAttribute("user", RequestAttributes.SCOPE_REQUEST);

    if (user == null) {
      throw new UserNotAuthenticatedException();
    }

    return user;
  }

  public User show() {
    return this.getAuthenticatedUser();
  }

  public User create(CreateUserDTO data) {
    
    if (this.usersRepository.existsByEmail(data.email())) {
      throw new UserEmailAlreadyExistsException();
    }

    User user = new User(data.name(), data.email(), data.password());
    return this.usersRepository.save(user);
  }

  public User update(UpdateUserDTO data) {
    User user = this.getAuthenticatedUser();

    Optional<String> email = data.email();

    if (email.isPresent()) {
      if (this.usersRepository.existsByEmail(email.get())) {
        throw new UserEmailAlreadyExistsException();
      } else {
        user.setEmail(email.get());
      }
    }

    data.name().ifPresent(user::setName);
    data.password().ifPresent(password -> user.setPassword(HashService.apply(password, user.getSalt())));

    return this.usersRepository.save(user);
  }

  public void delete() {
    User user = this.getAuthenticatedUser();
    this.usersRepository.delete(user);
  }
}
