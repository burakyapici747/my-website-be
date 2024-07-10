package com.blog.mywebsite.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ISO8601Validator implements ConstraintValidator<ISO8601Validation, LocalDate> {
    @Override
    public void initialize(ISO8601Validation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        try {
            DateTimeFormatter.ISO_LOCAL_DATE.format(value);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("burak" + e.getMessage());
            return false;
        }
    }
}
