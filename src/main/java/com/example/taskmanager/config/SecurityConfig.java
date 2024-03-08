package com.example.taskmanager.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;

import com.example.taskmanager.filters.JwtFilter;

import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class SecurityConfig implements WebApplicationInitializer {

  @Override
  public void onStartup(@NonNull ServletContext servletContext) {
    servletContext.addListener(RequestContextListener.class);
  }

  private final JwtFilter jwtFilter;

  @Bean
  FilterRegistrationBean<JwtFilter> jwtFilterRegistration() {
    FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(jwtFilter);
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
