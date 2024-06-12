package com.blog.mywebsite.constant;

import java.time.LocalDateTime;
import java.util.UUID;

public final class CategoryTestConstant {
    private CategoryTestConstant(){throw new AssertionError();}

    public static final String ID = UUID.randomUUID().toString();
    public static final String PARENT_ID = UUID.randomUUID().toString();
    public static final String NAME = "Java";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();

}
