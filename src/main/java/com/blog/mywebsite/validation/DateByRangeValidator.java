package com.blog.mywebsite.validation;

import com.blog.mywebsite.api.input.article.ArticleGetByDateRangeInput;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class DateByRangeValidator implements ConstraintValidator<DateByRangeValidation, ArticleGetByDateRangeInput> {
    @Override
    public void initialize(DateByRangeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ArticleGetByDateRangeInput articleGetByDateRange, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(articleGetByDateRange.from()) || Objects.isNull(articleGetByDateRange.to())){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("From and To parameters can not be null.")
                    .addConstraintViolation();
            return false;
        }

        if(articleGetByDateRange.from().isAfter(articleGetByDateRange.to())){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("From (%s) is after to (%s), which is invalid.", articleGetByDateRange.from(), articleGetByDateRange.to()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}