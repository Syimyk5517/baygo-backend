package com.example.baygo.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ImageUrlsValidator implements ConstraintValidator<ImageUrlsValid, List<String>> {
    private final ImageUrlValidator imageUrlValidator = new ImageUrlValidator();

    @Override
    public void initialize(ImageUrlsValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> imageUrls, ConstraintValidatorContext context) {
        if (imageUrls == null) {
            return true;
        }

        for (String imageUrl : imageUrls) {
            if (!imageUrlValidator.isValid(imageUrl, context)) {
                return false;
            }
        }

        return true;
    }
}
