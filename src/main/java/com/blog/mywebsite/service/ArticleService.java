package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.enumerator.SearchOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(
            String id,
            String categoryId,
            LocalDate publishDate,
            Integer readingTime
    );
    Map<Integer, List<ArticleDTO>> getGroupedArticlesByYear(
            LocalDate publishDate,
            SearchOperation searchOperation
    );
    Map<Integer, List<ArticleDTO>> getGroupedYearByCategoryName(String categoryName);
    List<ArticleDTO> getByDateRange(LocalDate startDate, LocalDate endDate);
    ArticleDTO deleteById(String id);
    ArticleDTO create(ArticlePostRequest articlePostRequest);
    ArticleDTO updateById(String id, ArticlePutRequest articlePutRequest);
}
