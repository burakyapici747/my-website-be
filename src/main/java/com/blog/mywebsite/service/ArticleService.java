package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    BaseResponse<ArticleDTO> getById(String id);
    BaseResponse<Map<Integer, List<ArticleDTO>>> getAllGroupedAndDecreasedByYear();
    BaseResponse<List<ArticleDTO>> getAll();
    BaseResponse<List<ArticleDTO>> getByDate(LocalDate date);
    BaseResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate);
    BaseResponse<Void> deleteById(String id);
    BaseResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest);
    BaseResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest);
}
