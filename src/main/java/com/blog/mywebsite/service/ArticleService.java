package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService {
    BaseResponse<ArticleDTO> getById(String id);
    BaseResponse<List<ArticleDTO>> getAll();
    BaseReponse<List<ArticleDTO>> getByDate(LocalDate date);
    BaseResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate);
    BaseResponse deleteById(String id);
    DataResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest);
    DataResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest);
}
