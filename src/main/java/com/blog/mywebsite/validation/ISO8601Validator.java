package com.blog.mywebsite.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ISO8601Validator implements ConstraintValidator<ISO8601Validation, LocalDateTime> {
    private static final Pattern ISO_8601_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");

    @Override
    public void initialize(ISO8601Validation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        try {
            // LocalDateTime formatı kontrolü
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").parse(value.toString());
        } catch (DateTimeParseException e) {
            return false;
        }

        // Minimum ve maksimum değer kontrolü (isteğe bağlı)
        if (value.isBefore(LocalDateTime.of(2000, 1, 1, 0, 0, 0)) ||
                value.isAfter(LocalDateTime.now())) {
            return false;
        }

        return true;
    }
}
