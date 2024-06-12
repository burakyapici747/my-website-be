package com.blog.mywebsite.util;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.constant.CategoryTestConstant;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.blog.mywebsite.constant.CategoryTestConstant.*;

public final class CategoryTestUtil {
    private CategoryTestUtil(){throw new AssertionError();}

    public static CategoryPostRequest createCategoryPostRequest(){
        return new CategoryPostRequest(
                null,
                NAME
        );
    }

    public static CategoryPostRequest categoryPostRequestWithParentId(){
        return new CategoryPostRequest(
                PARENT_ID,
                NAME
        );
    }

    public static CategoryPostRequest createInvalidCategoryPostRequestWithInvalidSizeParentId(){
        return new CategoryPostRequest(
                "invalidParentId",
                NAME
        );
    }

    public static Category createCategory(){
        Category category = new Category();
        List<Article> articleList = List.of(
                ArticleTestUtil.createArticle(),
                ArticleTestUtil.createArticle()
        );
        category.setId(ID);
        category.setName(NAME);
        category.setArticles(articleList);
        category.setCreatedAt(CREATED_AT);
        return category;
    }

    public static Category createParentCategory(){
        Category category = new Category();
        List<Article> articleList = List.of(
                ArticleTestUtil.createArticle(),
                ArticleTestUtil.createArticle()
        );
        category.setId(PARENT_ID);
        category.setName(NAME);
        category.setArticles(articleList);
        category.setCreatedAt(CREATED_AT);
        return category;
    }

    public static CategoryDTO createCategoryDTO(){
        return new CategoryDTO(
                ID,
                null,
                NAME,
                List.of()
        );
    }

    public static CategoryDTO createCategoryDTOWithParentId(){
        return new CategoryDTO(
                ID,
                PARENT_ID,
                NAME,
                List.of()
        );
    }
}
