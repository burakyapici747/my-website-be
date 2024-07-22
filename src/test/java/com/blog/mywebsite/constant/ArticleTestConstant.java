package com.blog.mywebsite.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public final class ArticleTestConstant {
    private ArticleTestConstant() {throw new AssertionError();}
    public static final String ID = UUID.randomUUID().toString();
    public static final String CATEGORY_ID = UUID.randomUUID().toString();
    public static final String TITLE = "Micro Service";
    public static final String CONTENT = "Content";
    public static final int RATE = 5;
    public static final int READING_TIME = 5;
    public static final String PUBLISH_DATE = "2024-05-05";
    public static final LocalDate PUBLISH_DATE_IN_DATE = LocalDate.parse(PUBLISH_DATE);
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
}
