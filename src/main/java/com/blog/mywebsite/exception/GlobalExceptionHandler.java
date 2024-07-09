package com.blog.mywebsite.exception;

import com.blog.mywebsite.api.response.ErrorResponse;
import com.blog.mywebsite.constant.APIConstant;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException
    ){
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        List<ErrorResponse.Error> errors = new ArrayList<>();
        violations.forEach(
                violation -> errors.add(
                        new ErrorResponse.Error(
                                "Deneme Id",
                                null,
                                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                "Constraint Violation Error",
                                violation.getMessage(),
                                new ErrorResponse.Error.Source(
                                        violation.getPropertyPath().toString(),
                                        "Bos parameter"
                                ),
                                new ErrorResponse.Error.Meta(
                                    UUID.randomUUID().toString(),
                                        LocalDateTime.now(),
                                        APIConstant.VERSION
                                )
                        )
                )
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException
    ){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(
                fieldError ->  errors.add(
                        new ErrorResponse.Error(
                                "Deneme id",
                                null,
                                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                "Method Argument Not Valid Error",
                                fieldError.getDefaultMessage(),
                                new ErrorResponse.Error.Source(
                                        fieldError.getField(),
                                        "Bos Parameter"
                                ),
                                new ErrorResponse.Error.Meta(
                                        UUID.randomUUID().toString(),
                                        LocalDateTime.now(),
                                        APIConstant.VERSION
                                )
                        )
                )
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException
    ){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(
            new ErrorResponse.Error(
                "Deneme id",
                null,
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Method Argument Not Valid Error",
                "Error Detail",
                new ErrorResponse.Error.Source(
                        "Pointer",
                        "Bos Parameter"
                ),
                new ErrorResponse.Error.Meta(
                        UUID.randomUUID().toString(),
                        LocalDateTime.now(),
                        APIConstant.VERSION
                )
            )
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistException entityExistException){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(
                new ErrorResponse.Error(
                        "Deneme id",
                        null,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        "Method Argument Not Valid Error",
                        "Error Detail",
                        new ErrorResponse.Error.Source(
                                "Pointer",
                                "Bos Parameter"
                        ),
                        new ErrorResponse.Error.Meta(
                                UUID.randomUUID().toString(),
                                LocalDateTime.now(),
                                APIConstant.VERSION
                        )
                )
        );
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UsernameNotFoundException usernameNotFoundException){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(
                new ErrorResponse.Error(
                        "Deneme id",
                        null,
                        String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        "Method Argument Not Valid Error",
                        "Error Detail",
                        new ErrorResponse.Error.Source(
                                "Pointer",
                                "Bos Parameter"
                        ),
                        new ErrorResponse.Error.Meta(
                                UUID.randomUUID().toString(),
                                LocalDateTime.now(),
                                APIConstant.VERSION
                        )
                )
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponse(errors));
    }
}