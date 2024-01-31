package com.blog.mywebsite.dto;

import com.blog.mywebsite.model.Article;

public record CommentDTO(Article article, String content, long rate) {
}
