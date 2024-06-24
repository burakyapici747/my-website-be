package com.blog.mywebsite.constant;

public final class SecurityConstant {

    private SecurityConstant(){
        throw new AssertionError();
    }
    public static final String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";
    public static final String SECRET_KEY = "Y!V4aZ_U!^a|N1Q";
    public static final int CURRENT_EXPIRED_DAY = 5;
    public static final String COOKIE_TOKEN = "token";
}