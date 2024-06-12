package com.blog.mywebsite.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableSizeValidator implements ConstraintValidator<NullableSize, String> {
    private int size;

    @Override
    public void initialize(NullableSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.size = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.length() == this.size;
    }
}
