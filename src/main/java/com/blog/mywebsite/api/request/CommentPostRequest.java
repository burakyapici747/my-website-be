package com.blog.mywebsite.api.request;

import com.blog.mywebsite.validation.NullableSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentPostRequest(
        @NotBlank(message = "ArticleId cannot be empty or null.")
        @Size(max = 36, message = "ArticleId field must be 36 characters long.")
        String articleId,
        @NullableSize(message = "CommentParentId field must be empty or 36 characters long.")
        String parentId,
        @NotBlank(message = "Content cannot be empty or null.")
        @Size(max = 100, message = "Title field must be between 5 and 255 characters long.")
        String content
) { }
