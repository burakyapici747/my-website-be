package com.blog.mywebsite.util;

import com.blog.mywebsite.api.input.article.ArticlePostInput;
import com.blog.mywebsite.constant.ArticleTestConstant;
import com.blog.mywebsite.constant.CommentTestConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.model.Comment;

import java.util.List;

public final class ArticleTestUtil {
    private ArticleTestUtil() {throw new AssertionError();}

    public static ArticlePostInput createArticlePostInput(){
        return new ArticlePostInput(
                ArticleTestConstant.CATEGORY_ID,
                ArticleTestConstant.TITLE,
                ArticleTestConstant.CONTENT,
                ArticleTestConstant.READING_TIME,
                ArticleTestConstant.PUBLISH_DATE
        );
    }

    public static Article createArticleWithComment(){
        Article article = createArticle();
        article.getComments().addAll(CommentTestUtil.createCommentList());
        return article;
    }

    public static ArticlePostInput createArticlePostInputWithNullCategoryId(){
        return new ArticlePostInput(
                null,
                ArticleTestConstant.TITLE,
                ArticleTestConstant.CONTENT,
                ArticleTestConstant.READING_TIME,
                ArticleTestConstant.PUBLISH_DATE
        );
    }

    public static ArticleDTO createArticleDTO(){
        return new ArticleDTO(
                ArticleTestConstant.ID,
                ArticleTestConstant.TITLE,
                ArticleTestConstant.CONTENT,
                ArticleTestConstant.READING_TIME,
                ArticleTestConstant.RATE,
                ArticleTestConstant.PUBLISH_DATE_IN_DATE,
                ArticleTestConstant.CREATED_AT
        );
    }

    public static Article createArticle(){
        Article article = new Article();
        article.setId(ArticleTestConstant.ID);
        article.setCategory(null);
        article.setContent(ArticleTestConstant.CONTENT);
        article.setTitle(ArticleTestConstant.TITLE);
        article.setRate(ArticleTestConstant.RATE);
        article.setPublishDate(ArticleTestConstant.PUBLISH_DATE_IN_DATE);
        article.setReadingTime(ArticleTestConstant.READING_TIME);
        article.setCreatedAt(ArticleTestConstant.CREATED_AT);
        return article;
    }
}