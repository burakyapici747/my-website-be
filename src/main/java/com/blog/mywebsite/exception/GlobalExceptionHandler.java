package com.blog.mywebsite.exception;

import com.blog.mywebsite.api.response.ErrorResponse;
import com.blog.mywebsite.constant.ResponseConstant;
import com.blog.mywebsite.util.ErrorResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            HttpServletRequest httpServletRequest
    ){
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        List<ErrorResponse.Error> errors = new ArrayList<>();
        violations.forEach(
                violation -> errors.add(
                        ErrorResponseFactory.createErrorResponse(
                                constraintViolationException,
                                httpServletRequest,
                                HttpStatus.BAD_REQUEST,
                                ResponseConstant.ERROR_CONSTRAINT_VIOLATION_EXCEPTION_TITLE,
                                ResponseConstant.ERROR_CONSTRAINT_VIOLATION_EXCEPTION
                        )
                )
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletRequest httpServletRequest
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                processErrors(
                        methodArgumentNotValidException,
                        httpServletRequest
                )
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException entityNotFoundException,
            HttpServletRequest httpServletRequest
    ){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(
                ErrorResponseFactory.createErrorResponse(
                        entityNotFoundException,
                        httpServletRequest,
                        HttpStatus.NOT_FOUND,
                        entityNotFoundException.getMessage(),
                        ResponseConstant.ERROR_NOT_FOUND_DETAILS_MESSAGE
                )
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(
            EntityExistException entityExistException,
            HttpServletRequest httpServletRequest
    ){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(
                ErrorResponseFactory.createErrorResponse(
                        entityExistException,
                        httpServletRequest,
                        HttpStatus.CONFLICT,
                        ResponseConstant.ERROR_CONFLICT,
                        entityExistException.getMessage()
                )
        );
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(new ErrorResponse(errors));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UsernameNotFoundException usernameNotFoundException,
            HttpServletRequest httpServletRequest
    ){
        List<ErrorResponse.Error> errors = new ArrayList<>();
        errors.add(
                ErrorResponseFactory.createErrorResponse(
                        usernameNotFoundException,
                        httpServletRequest,
                        HttpStatus.NOT_FOUND,
                        usernameNotFoundException.getMessage(),
                        ResponseConstant.ERROR_NOT_FOUND_DETAILS_MESSAGE
                )
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponse(errors));
    }

    private ErrorResponse processErrors(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletRequest httpServletRequest
    ){
        List<ErrorResponse.Error> validationErrorModels = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            ErrorResponse.Error validationError = ErrorResponseFactory.createErrorResponse(
                    methodArgumentNotValidException,
                    httpServletRequest,
                    HttpStatus.BAD_REQUEST,
                    ResponseConstant.ERROR_INVALID_INPUT,
                    error.getObjectName() + " " + error.getDefaultMessage()
            );
            validationErrorModels.add(validationError);
        });

        return new ErrorResponse(validationErrorModels);
    }
}