package com.example.baygo.db.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NameValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^[a-zA-Zа-яА-Я ]+$") && value.length() >= 2 && value.length() <= 40;
    }
}

