package com.example.baygo.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {
    String message() default "Пароль должен содержать не менее одной буквы, одну цифру и иметь длину не менее 8 символов!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
