package com.example.baygo.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageUrlsValidator.class)
@Documented
public @interface ImageUrlsValid {

    String message() default "One or more image URLs have invalid extensions.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
