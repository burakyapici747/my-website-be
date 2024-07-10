package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record CommentPutRequest(
        @Size(min = COMMENT_CONTENT_MIN_LENGTH, max = COMMENT_CONTENT_MAX_LENGTH, message = COMMENT_CONTENT_SIZE_MESSAGE)
        String content
) { }
