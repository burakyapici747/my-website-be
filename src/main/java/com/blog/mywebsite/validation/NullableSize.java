package com.blog.mywebsite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullableSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableSize {
    String message() default "Field must be empty or 36 characters long.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int size() default 36;
}
