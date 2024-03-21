package com.blog.mywebsite.exception;

import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.ErrorDataResponse;
import com.blog.mywebsite.api.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler{
    //TODO message içeriğini düzelt
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Map<String, String>> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException
    ){
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        violations.forEach(violation -> {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return new ErrorDataResponse<>(HttpStatus.BAD_REQUEST.value(), "", errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDataResponse<Object> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException
    ){

        return new ErrorDataResponse<>(HttpStatus.NOT_FOUND.value(), entityNotFoundException.getMessage(), null);
    }

    @ExceptionHandler(EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse handleEntityExistsException(EntityExistException entityExistException){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), entityExistException.getMessage());
    }

}
