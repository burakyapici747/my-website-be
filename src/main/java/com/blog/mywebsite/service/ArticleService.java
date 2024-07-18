package com.blog.mywebsite.service;

import com.blog.mywebsite.api.input.article.ArticlePostInput;
import com.blog.mywebsite.api.input.article.ArticlePutInput;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.enumerator.SearchOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(String id, String title, LocalDate publishDate, Integer readingTime);
    Map<Integer, List<ArticleDTO>> getGroupedArticlesByYear(LocalDate publishDate, SearchOperation searchOperation);
    Map<Integer, List<ArticleDTO>> getGroupedYearByCategoryName(String categoryName);
    List<ArticleDTO> getByDateRange(LocalDate startDate, LocalDate endDate);
    ArticleDTO deleteById(String id);
    ArticleDTO create(ArticlePostInput articlePostInput);
    ArticleDTO updateById(String id, ArticlePutInput articlePutInput);
}
