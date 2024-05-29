package com.blog.mywebsite.util;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;

import java.time.LocalDate;
import java.util.UUID;

import static com.blog.mywebsite.constant.ArticleTestConstant.*;

public final class ArticleTestUtil {
    private ArticleTestUtil() {

    }

    public static ArticlePostRequest createArticlePostRequest() {
        return new ArticlePostRequest(
                TITLE,
                CONTENT,
                READING_TIME,
                PUBLISH_DATE
        );
    }

    public static Article createArticle() {
        Article article = new Article();

        article.setId(UUID.randomUUID().toString());
        article.setRate(RATE);
        article.setTitle(TITLE);
        article.setContent(CONTENT);
        article.setReadingTime(READING_TIME);
        article.setPublishDate(PUBLISH_DATE_IN_DATE);
        article.setCreatedAt(CREATED_AT);

        return article;
    }

    public static ArticleDTO createArticleDTO() {
        return new ArticleDTO(
                TITLE,
                CONTENT,
                READING_TIME,
                RATE,
                PUBLISH_DATE_IN_DATE,
                CREATED_AT
        );
    }
}
