package com.blog.mywebsite.service;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.DataResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;

import java.util.List;

public interface ArticleService {
    DataResponse<ArticleDTO> getById(String id);
    DataResponse<List<ArticleDTO>> getAll();
    BaseResponse deleteById(String id);
    DataResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest);
    DataResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest);
    Article findById(String id);
}
