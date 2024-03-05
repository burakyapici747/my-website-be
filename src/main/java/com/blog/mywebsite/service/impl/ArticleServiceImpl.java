package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.DataResponse;
import com.blog.mywebsite.api.response.SuccessDataResponse;
import com.blog.mywebsite.api.response.SuccessResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.ArticleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public DataResponse<ArticleDTO> getById(String id) {
        final ArticleDTO articleDTOs = ArticleMapper.INSTANCE.articleToArticleDTO(findById(id));

        return new SuccessDataResponse<>(articleDTOs, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public DataResponse<List<ArticleDTO>> getByDate(LocalDate date) {
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.INSTANCE.articlesToArticleDTOs(articleRepository.findByPublishDate(date));

        return new SuccessDataResponse<>(articleDTOs, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public DataResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate){
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(articleRepository.findByPublishDateBetween(startDate, endDate));

        return new SuccessDataResponse<>(articleDTOs, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public DataResponse<List<ArticleDTO>> getAll() {
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(articleRepository.findAll());

        return new SuccessDataResponse<>(articleDTOs, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public DataResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest) {
        Article article = new Article();

        article.setTitle(articlePostRequest.title());
        article.setContent(articlePostRequest.content());
        article.setReadingTime(articlePostRequest.readingTime());
        article.setRate(0);
        article.setPublishDate(LocalDate.parse(articlePostRequest.publishDate()));

        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));

        return new SuccessDataResponse<>(articleDTO, EntityConstant.SUCCESS_CREATE);
    }

    @Override
    public DataResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest) {
        final Article article = findById(id);

        ArticleMapper.INSTANCE.articlePutRequestToArticleDTO(articlePutRequest, article);

        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));

        return new SuccessDataResponse<>(articleDTO, EntityConstant.SUCCESS_UPDATE);
    }

    public Article findById(String id){
        return articleRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(EntityConstant.ARTICLE_NOT_FOUND));
    }

    @Override
    public BaseResponse deleteById(String id) {
        final Article article = findById(id);

        articleRepository.delete(article);

        return new SuccessResponse(EntityConstant.SUCCESS_DELETE);
    }
}