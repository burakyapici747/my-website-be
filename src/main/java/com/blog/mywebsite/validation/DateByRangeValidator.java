package com.blog.mywebsite.validation;

import com.blog.mywebsite.api.request.ArticleGetByDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateByRangeValidator implements ConstraintValidator<DateByRangeValidation, ArticleGetByDateRange> {
    @Override
    public void initialize(DateByRangeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ArticleGetByDateRange articleGetByDateRange, ConstraintValidatorContext constraintValidatorContext) {
        if(articleGetByDateRange.from().isAfter(articleGetByDateRange.to())){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("From (%s) is after to (%s), which is invalid.", articleGetByDateRange.from(), articleGetByDateRange.to()))
                    .addConstraintViolation();
            return false;
        }else if(articleGetByDateRange.to().isAfter(articleGetByDateRange.from())){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("To (%s) is after from (%s), which is invalid.", articleGetByDateRange.to(), articleGetByDateRange.from()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}