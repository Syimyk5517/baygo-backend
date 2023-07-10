package com.example.baygo.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String > {
    String pattern = "^\\+?\\d{1,3}[-.\\s]?\\(?" +
            "\\d{1,3}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(phoneNumber.length() == 13){
            return true;
        }
        return pattern.matches(phoneNumber);
    }
}
