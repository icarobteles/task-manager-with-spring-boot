package com.example.taskmanager.validators;

import java.util.regex.Pattern;

import com.example.taskmanager.annotations.StrongPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
  private static final Pattern UPPERCASE = Pattern.compile("(?=.*[A-Z]).+");
  private static final Pattern LOWERCASE = Pattern.compile("(?=.*[a-z]).+");
  private static final Pattern DIGIT = Pattern.compile("(?=.*\\d).+");
  private static final Pattern SPECIAL_CHAR = Pattern.compile("(?=.*[@#$%^&+=]).+");

  private static final int MIN_LENGTH = 8;

  @Override
  public void initialize(StrongPassword constraintAnnotation) {
      // Inicialização do validador, se necessário
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null) {
        return false;
    }

    if (!UPPERCASE.matcher(password).matches()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("A senha deve ter pelo menos uma letra maiúscula")
                .addConstraintViolation();
        return false;
    }

    if (!LOWERCASE.matcher(password).matches()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("A senha deve ter pelo menos uma letra minúscula")
                .addConstraintViolation();
        return false;
    }

    if (!DIGIT.matcher(password).matches()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("A senha deve ter pelo menos um número")
                .addConstraintViolation();
        return false;
    }

    if (!SPECIAL_CHAR.matcher(password).matches()) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("A senha deve conter pelo menos um caractere especial")
                .addConstraintViolation();
        return false;
    }

    if (password.length() < MIN_LENGTH) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("A senha deve ter pelo menos 8 caracteres")
                .addConstraintViolation();
        return false;
    }

    return true;

  }
}