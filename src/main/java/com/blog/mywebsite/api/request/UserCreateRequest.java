package com.blog.mywebsite.api.request;

public record UserCreateRequest(
        String email,
        String name
) { }

