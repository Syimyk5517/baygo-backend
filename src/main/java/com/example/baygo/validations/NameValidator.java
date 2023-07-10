package com.example.baygo.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NameValid, String> {
    private final Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return pattern.matcher(value).matches();
    }
}

