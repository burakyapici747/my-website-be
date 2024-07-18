package com.blog.mywebsite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISO8601Validator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISO8601Validation {
    String message() default "Please enter a date in ISO 8601 format. For example, 'yyyy-MM-dd'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}