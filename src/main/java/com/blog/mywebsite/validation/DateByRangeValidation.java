package com.blog.mywebsite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateByRangeValidator.class)
@Target({ElementType.TYPE,  ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateByRangeValidation {
    String message() default "`from` should be more recent then `to`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
