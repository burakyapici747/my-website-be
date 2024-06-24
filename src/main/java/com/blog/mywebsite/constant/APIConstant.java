package com.blog.mywebsite.constant;

public final class APIConstant {
    private APIConstant(){
        throw new AssertionError();
    }
    public static final String VERSION = "/v1";
    public static final String BASE_URL = VERSION + "/api";

    public static final String USER_URL = BASE_URL + "/user";
    public static final String USER_LOGIN_URL = USER_URL + "/login";
    public static final String USER_REGISTER_URL = USER_URL + "/register";
    public static final String ALL_USER_URL = BASE_URL + "/user/**";
    public static final String CATEGORY_URL = BASE_URL + "/category";
    public static final String COMMENT_URL = BASE_URL + "/comment";
    public static final String COMMENTS_URL = "/comments";
    public static final String ALL_CATEGORY_URL = BASE_URL + "/category/**";
    public static final String ALL_COMMENT_URL = BASE_URL + "/comment/**";

    //Article
    public static final String ARTICLE_URL = BASE_URL + "/article";
    public static final String ALL_ARTICLE_URL = BASE_URL + "/article/**";
    public static final String ARTICLES_BY_DATE_RANGE = "/by-date-range";
    public static final String ARTICLES_GROUPED_BY_YEAR = "/grouped-by-year";
    public static final String ARTICLES_GROUPED_BY_YEAR_BY_CATEGORY = "/by-category";
}