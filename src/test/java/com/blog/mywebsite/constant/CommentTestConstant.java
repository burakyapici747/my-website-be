package com.blog.mywebsite.constant;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentTestConstant {
    private CommentTestConstant(){
        throw new AssertionError();
    }

    public static final String ID = UUID.randomUUID().toString();
    public static final String CONTENT = "Test Content";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public static final String PARENT_ID = UUID.randomUUID().toString();
    public static final String PARENT_CONTENT = "Parent Content1";
    public static final LocalDateTime PARENT_CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime PARENT_UPDATED_AT = LocalDateTime.now();

    public static final String SUB_ID = UUID.randomUUID().toString();
    public static final String SUB_CONTENT = "Sub Content1";
    public static final LocalDateTime SUB_CREATED_AT = LocalDateTime.now();
    public static final LocalDateTime SUB_UPDATED_AT = LocalDateTime.now();
}