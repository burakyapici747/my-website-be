package com.blog.mywebsite.api.response;

public record Links(
        String self,
        String related,
        String next,
        String last
){}