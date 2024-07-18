package com.blog.mywebsite.api.input.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.blog.mywebsite.constant.ValidationConstant.*;

public record CommentPostInput(
        @NotBlank(message = ARTICLE_ID_BLANK_MESSAGE)
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = ARTICLE_ID_SIZE_MESSAGE)
        String articleId,
        @Size(min = ID_MIN_LENGTH, max = ID_MAX_LENGTH, message = COMMENT_PARENT_ID_SIZE_MESSAGE)
        String parentId,
        @NotBlank(message = COMMENT_CONTENT_BLANK_MESSAGE)
        @Size(min = COMMENT_CONTENT_MIN_LENGTH, max = COMMENT_CONTENT_MAX_LENGTH, message = COMMENT_CONTENT_SIZE_MESSAGE)
        String content
){}