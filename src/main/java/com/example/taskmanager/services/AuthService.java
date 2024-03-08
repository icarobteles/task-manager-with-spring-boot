package com.example.taskmanager.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanager.dtos.auth.AuthResponseDTO;
import com.example.taskmanager.dtos.auth.LoginDTO;
import com.example.taskmanager.dtos.auth.RefreshDTO;
import com.example.taskmanager.dtos.auth.RegisterDTO;
import com.example.taskmanager.entities.User;
import com.example.taskmanager.exceptions.auth.ExpiredJwtTokenException;
import com.example.taskmanager.exceptions.hash.MismatchedPasswordsException;
import com.example.taskmanager.exceptions.users.UserEmailAlreadyExistsException;
import com.example.taskmanager.exceptions.users.UserNotFoundException;
import com.example.taskmanager.repositories.UsersRepository;
import com.example.taskmanager.singletons.JwtServiceSingleton;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {
  private final UsersRepository usersRepository;

  private final JwtService jwtService = JwtServiceSingleton.getInstance();

  public AuthResponseDTO login(LoginDTO data) {
    String email = data.email();
    String password = data.password();

    User user = this.usersRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

    if (!HashService.compare(password, user.getPassword(), user.getSalt())) {
      throw new MismatchedPasswordsException();
    }

    String[] tokens = jwtService.generateTokens(user.getId());

    return new AuthResponseDTO(tokens[0], tokens[1]);
  }

  public AuthResponseDTO register(RegisterDTO data) {
    String name = data.name();
    String email = data.email();
    String password = data.password();

    if (this.usersRepository.existsByEmail(email)) {
      throw new UserEmailAlreadyExistsException();
    }

    User user = new User(name, email, password);

    this.usersRepository.save(user);

    String[] tokens = jwtService.generateTokens(user.getId());

    return new AuthResponseDTO(tokens[0], tokens[1]);
  }

  public AuthResponseDTO refresh(RefreshDTO data) {
    String refreshToken = data.refreshToken();
    DecodedJWT decodedRefreshToken = jwtService.decodeToken(refreshToken);
    UUID id = UUID.fromString(decodedRefreshToken.getSubject());

    Instant now = Instant.now();
    Instant expiration = decodedRefreshToken.getExpiresAt().toInstant();

    if (now.isAfter(expiration)) {
      throw new ExpiredJwtTokenException("Token de atualização expirado, você precisa fazer login novamente.");
    }

    String[] tokens = jwtService.generateTokens(id);
    return new AuthResponseDTO(tokens[0], tokens[1]);
  }
}