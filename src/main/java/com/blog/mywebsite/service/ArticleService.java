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
    BaseResponse<List<ArticleDTO>> getArticles(
            SearchOperation searchOperation,
            String id,
            LocalDate publishDate,
            int rate,
            int readingTime,
            String categoryName
    );
    BaseResponse<Map<Integer, List<ArticleDTO>>> getGroupedArticlesByYear(
            LocalDate publishDate,
            SearchOperation searchOperation
    );
    BaseResponse<Map<Integer, List<ArticleDTO>>> getGroupedYearByCategoryName(String categoryName);
    BaseResponse<List<ArticleDTO>> getAll();
    BaseResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate);
    BaseResponse<Void> deleteById(String id);
    BaseResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest);
    BaseResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest);
}
