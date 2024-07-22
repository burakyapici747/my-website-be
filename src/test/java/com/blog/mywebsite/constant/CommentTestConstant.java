package com.blog.mywebsite.constant;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentTestConstant {
    private CommentTestConstant(){
        throw new AssertionError();
    }

    public static String ID = UUID.randomUUID().toString();
    public static String PARENT_ID = UUID.randomUUID().toString();
    public static String CONTENT = "Test Content";
    public static String PARENT_CONTENT = "Parent Content";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
}