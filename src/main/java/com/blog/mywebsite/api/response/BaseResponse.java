package com.blog.mywebsite.api.response;

public record BaseResponse<T>(
        Links links,
        T data
){}