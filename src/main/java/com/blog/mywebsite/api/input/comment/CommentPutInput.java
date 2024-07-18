package com.blog.mywebsite.api.input.comment;

import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record CommentPutInput(
        @Size(min = COMMENT_CONTENT_MIN_LENGTH, max = COMMENT_CONTENT_MAX_LENGTH, message = COMMENT_CONTENT_SIZE_MESSAGE)
        String content
) {}