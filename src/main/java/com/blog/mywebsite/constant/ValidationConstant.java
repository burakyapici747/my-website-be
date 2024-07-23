package com.blog.mywebsite.constant;

public class ValidationConstant {
    private ValidationConstant() {
        throw new AssertionError();
    }

    // Ortak String Parçaları
    private static final String CHARACTERS_LONG = " characters long.";
    private static final String FIELD_MUST_BE_EMPTY_OR = " field must be empty or ";
    private static final String FIELD_MUST_BE_BETWEEN = " field must be between ";
    private static final String AND = " and ";
    private static final String BLANK_MESSAGE = " cannot be empty or null";
    private static final String POSITIVE_MESSAGE = " field must be positive.";
    private static final String CONTENT = "Content";

    // ID Sabitleri
    public static final int ID_MIN_LENGTH = 36;
    public static final int ID_MAX_LENGTH = 36;
    public static final String ID_SIZE_MESSAGE = "Id" + FIELD_MUST_BE_EMPTY_OR + ID_MAX_LENGTH + CHARACTERS_LONG;
    public static final String ARTICLE_ID_BLANK_MESSAGE = "ArticleId" + BLANK_MESSAGE;
    public static final String ARTICLE_ID_SIZE_MESSAGE = "ArticleId" + ID_SIZE_MESSAGE;

    // Article Title Sabitleri
    public static final int ARTICLE_TITLE_MIN_LENGTH = 5;
    public static final int ARTICLE_TITLE_MAX_LENGTH = 255;
    public static final String ARTICLE_TITLE_MESSAGE = "Title" + FIELD_MUST_BE_EMPTY_OR + ARTICLE_TITLE_MIN_LENGTH + AND + ARTICLE_TITLE_MAX_LENGTH + CHARACTERS_LONG;
    public static final String ARTICLE_TITLE_BLANK_MESSAGE = "Title" + BLANK_MESSAGE;

    // Article Content Sabitleri
    public static final int ARTICLE_CONTENT_MIN_LENGTH = 5;
    public static final int ARTICLE_CONTENT_MAX_LENGTH = 1000;
    public static final String ARTICLE_CONTENT_MESSAGE = CONTENT + FIELD_MUST_BE_BETWEEN + ARTICLE_CONTENT_MIN_LENGTH + AND + ARTICLE_CONTENT_MAX_LENGTH + CHARACTERS_LONG;
    public static final String ARTICLE_CONTENT_BLANK_MESSAGE = CONTENT + BLANK_MESSAGE;

    public static final String READING_TIME_MESSAGE = "ReadingTime" + POSITIVE_MESSAGE;

    public static final int CATEGORY_NAME_MIN_LENGTH = 3;
    public static final int CATEGORY_NAME_MAX_LENGTH = 55;
    public static final String CATEGORY_ID_SIZE_MESSAGE = "CategoryId" + ID_SIZE_MESSAGE;
    public static final String CATEGORY_PARENT_ID_MESSAGE = "ParentId" + ID_SIZE_MESSAGE;
    public static final String CATEGORY_ID_BLANK_MESSAGE = "CategoryId cannot be empty or null.";
    public static final String CATEGORY_NAME_BLANK_MESSAGE = "Name" + BLANK_MESSAGE;
    public static final String CATEGORY_NAME_SIZE_MESSAGE = "Name" + FIELD_MUST_BE_BETWEEN + CATEGORY_NAME_MIN_LENGTH + AND + CATEGORY_NAME_MAX_LENGTH + CHARACTERS_LONG;

    public static final int COMMENT_CONTENT_MIN_LENGTH = 3;
    public static final int COMMENT_CONTENT_MAX_LENGTH = 100;
    public static final String COMMENT_PARENT_ID_SIZE_MESSAGE = "ParentId" + ID_SIZE_MESSAGE;
    public static final String COMMENT_CONTENT_BLANK_MESSAGE = CONTENT + BLANK_MESSAGE;
    public static final String COMMENT_CONTENT_SIZE_MESSAGE = CONTENT + FIELD_MUST_BE_BETWEEN + COMMENT_CONTENT_MIN_LENGTH + AND + COMMENT_CONTENT_MAX_LENGTH + CHARACTERS_LONG;
}