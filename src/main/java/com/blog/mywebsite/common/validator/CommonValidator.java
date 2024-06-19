package com.blog.mywebsite.common.validator;

import jakarta.validation.*;
import org.springframework.util.ObjectUtils;

import java.util.Set;

public final class CommonValidator {
    private CommonValidator(){throw new AssertionError();}

    public static <T> void validateInput(T data){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(data);

        if(!ObjectUtils.isEmpty(violations)){
            throw new ConstraintViolationException(violations);
        }
    }
}
