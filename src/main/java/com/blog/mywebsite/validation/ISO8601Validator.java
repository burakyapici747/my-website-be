package com.blog.mywebsite.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ISO8601Validator implements ConstraintValidator<ISO8601Validation, String> {

    @Override
    public void initialize(ISO8601Validation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(value);

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
