package com.example.taskmanager.filters;

import java.io.IOException;
import java.util.UUID;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanager.core.HttpException;
import com.example.taskmanager.entities.User;
import com.example.taskmanager.exceptions.users.UserNotFoundException;
import com.example.taskmanager.repositories.UsersRepository;
import com.example.taskmanager.services.JwtService;
import com.example.taskmanager.singletons.JwtServiceSingleton;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@DependsOn("jwtService")
@AllArgsConstructor
public class JwtFilter implements Filter {

  private final UsersRepository usersRepository;

  private final JwtService jwtService = JwtServiceSingleton.getInstance();

  private static final String[] PUBLIC_START_URIS = { "/auth", "/h2" };

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    if (isPublicUri(req.getRequestURI())) {
      chain.doFilter(request, response);
      return;
    }

    String authHeader = req.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String accessToken = extractToken(authHeader);

      try {
        if (jwtService.validateAccessToken(accessToken)) {
          DecodedJWT decodedToken = jwtService.decodeToken(accessToken);
          UUID id = UUID.fromString(decodedToken.getSubject());
          User user = this.usersRepository.findById(id).orElseThrow(UserNotFoundException::new); 
          RequestContextHolder.currentRequestAttributes().setAttribute("user", user, RequestAttributes.SCOPE_REQUEST);
          chain.doFilter(request, response);
        } else {
          res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
        }
      } catch (HttpException e) {
        res.sendError(e.getStatusCode(), e.getMessage());
      }

      return;
    }

    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token não fornecido ou mal formatado");
  }

  private String extractToken(String authHeader) {
    return authHeader.substring(7);
  }

  private boolean isPublicUri(String uri) {

    for (String publicUri : PUBLIC_START_URIS) {
      if (uri.startsWith(publicUri)) {
        return true;
      }
    }

    return false;
  }
  
}
