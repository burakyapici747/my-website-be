package com.blog.mywebsite.constant;

public class APIConstant {
    private APIConstant(){
        throw new AssertionError();
    }

    public static final String BASE_URL = "/api";
    public static final String LOGIN = BASE_URL + "/user/login";
    public static final String CATEGORY_URL = BASE_URL + "/category";
    public static final String ARTICLE_URL = BASE_URL + "/article";
    public static final String COMMENT_URL = BASE_URL + "/comment";
    public static final String USER_URL = BASE_URL + "/user";
    public static final String ALL_CATEGORY = BASE_URL + "/category/**";
    public static final String ALL_ARTICLE = BASE_URL + "/article/**";
    public static final String ALL_COMMENT = BASE_URL + "/comment/**";
    public static final String ALL_USER = BASE_URL + "/user/**";
}