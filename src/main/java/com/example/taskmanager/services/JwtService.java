package com.example.taskmanager.services;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanager.exceptions.auth.InvalidJwtTokenException;
import com.example.taskmanager.exceptions.auth.InvalidSignatureTokenException;
import com.example.taskmanager.singletons.DotenvSingleton;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Service
public class JwtService {

  private static final Dotenv dotenv = DotenvSingleton.getInstance();

  private final String jwtSecretTokenAccess = dotenv.get("JWT_SECRET_TOKEN_ACCESS");
  private final String jetSecretTokenRefresh = dotenv.get("JWT_SECRET_TOKEN_REFRESH");

  @Getter
  private final Algorithm accessAlgorithm = Algorithm.HMAC256(jwtSecretTokenAccess);

  @Getter
  private final Algorithm refreshAlgorithm = Algorithm.HMAC256(jetSecretTokenRefresh);

  private String generateToken(UUID id, long secondsToAdd, Algorithm algorithm) {
    Instant exp = Instant.now().plusSeconds(secondsToAdd);
    return JWT.create().withSubject(id.toString()).withExpiresAt(exp).sign(algorithm);
  }

  public String generateAccessToken(UUID id) {
    return generateToken(id, TimeUnit.MINUTES.toSeconds(15), accessAlgorithm);
  }

  public String generateRefreshToken(UUID id) {
    return generateToken(id, TimeUnit.DAYS.toSeconds(7), refreshAlgorithm);
  }

  public String[] generateTokens(UUID id) {
    String accessToken = generateAccessToken(id);
    String refreshToken = generateRefreshToken(id);
    return new String[] { accessToken, refreshToken };
  }

  private boolean validateToken(String token, Algorithm algorithm) {
    try {
      JWT.require(algorithm).build().verify(token);
      return true;
    } catch (JWTDecodeException e) {
      throw new InvalidJwtTokenException();
    } catch (JWTVerificationException e) {
      throw new InvalidSignatureTokenException();
    }
  }

  public boolean validateAccessToken(String token) {
    return validateToken(token, accessAlgorithm);
  }

  public boolean validateRefreshToken(String token) {
    return validateToken(token, refreshAlgorithm);
  }

  public String extractTokenFromAuthHeader(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return null;
    }
    return authHeader.substring(7);
  }

  public DecodedJWT decodeToken(String token) {
    try {
      return JWT.decode(token);
    } catch (JWTDecodeException e) {
      throw new InvalidJwtTokenException();
    }
  }

}
