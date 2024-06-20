package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.Size;

public record CommentPutRequest(
        @Size(max = 100, message = "Content field maximum must be 100 characters long.")
        String content
) { }
