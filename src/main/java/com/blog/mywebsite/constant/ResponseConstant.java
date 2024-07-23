package com.blog.mywebsite.constant;

public final class ResponseConstant {
    private ResponseConstant(){
        throw new AssertionError();
    }

    public static final String ERROR_NOT_FOUND_DETAILS_MESSAGE = "The requested resource could not be found on this server.";
    public static final String ERROR_CONSTRAINT_VIOLATION_EXCEPTION_TITLE = "Constraint Violation Exception";
    public static final String ERROR_INVALID_INPUT = "Invalid Input";
    public static final String ERROR_CONFLICT = "Conflict Detected";
    public static final String ERROR_CONSTRAINT_VIOLATION_EXCEPTION = "The requested resource could not be found on this server.";
}