package com.example.taskmanager.annotations;

import java.lang.annotation.*;

import com.example.taskmanager.validators.StrongPasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {
  String message() default "A senha informada não é forte o suficiente";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
