package com.blog.mywebsite.api.output;

public record CommentOutput(
        String type,
        ArticleOutput.Attributes attributes
){
    public record Attributes(
            String id,
            String articleId,
            String content
    ){}
}