package com.blog.mywebsite.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}