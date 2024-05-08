package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.SuccessfulResponse;
import com.blog.mywebsite.api.response.SuccessfulDataResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.ArticleService;
import org.springframework.http.HttpStatus;
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
    public BaseResponse<ArticleDTO> getById(String id) {
        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(findById(id));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTO);
    }

//    @Override
//    public BaseResponse<List<ArticleDTO>> getByYear(int year) {
//        final List<ArticleDTO> articleDTOs =
//                ArticleMapper.INSTANCE.articlesToArticleDTOs(articleRepository.findByPublishDateYear(year));
//
//        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOs);
//    }

    @Override
    public BaseResponse<List<ArticleDTO>> getByDate(LocalDate date) {
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.INSTANCE.articlesToArticleDTOs(articleRepository.findByPublishDate(date));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOs);
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate){
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(articleRepository.findByPublishDateBetween(startDate, endDate));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOs);
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getAll() {
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(articleRepository.findAll());

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOs);
    }

    @Override
    public BaseResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest) {
        final Article article = new Article();

        article.setTitle(articlePostRequest.title());
        article.setContent(articlePostRequest.content());
        article.setReadingTime(articlePostRequest.readingTime());
        article.setRate(0);
        article.setPublishDate(LocalDate.parse(articlePostRequest.publishDate()));

        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_CREATE, articleDTO);
    }

    @Override
    public BaseResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest) {
        final Article article = findById(id);

        ArticleMapper.INSTANCE.articlePutRequestToArticleDTO(articlePutRequest, article);

        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_UPDATE, articleDTO);
    }


    @Override
    public BaseResponse<Void> deleteById(String id) {
        final Article article = findById(id);

        articleRepository.delete(article);

        return new SuccessfulResponse(HttpStatus.OK.value(), EntityConstant.SUCCESS_DELETE);
    }

    private Article findById(String id){
        return articleRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }
}