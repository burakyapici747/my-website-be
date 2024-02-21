package com.blog.mywebsite.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISO8601Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISO8601Validation {
    String message() default "Geçersiz tarih veya saat. Karşılaştırılabilecek ISO 8601 formatında olmalıdır.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}