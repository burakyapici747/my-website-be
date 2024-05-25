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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    @Override
    public BaseResponse<Map<Integer, List<ArticleDTO>>> getAllGroupedAndDecreasedByYear() {
        final List<ArticleDTO> articleDTOs = ArticleMapper.INSTANCE.articlesToArticleDTOs(findAll());
        final Map<Integer, List<ArticleDTO>> groupedArticlesDescendingByYear = articleDTOs.stream()
                .collect(Collectors.groupingBy(articleDTO -> articleDTO.publishDate().getYear(),
                        TreeMap::new,
                        Collectors.toList()));

        final Map<Integer, List<ArticleDTO>> sortedGroupedArticlesDescendingByYear = new TreeMap<>(Collections.reverseOrder());
        sortedGroupedArticlesDescendingByYear.putAll(groupedArticlesDescendingByYear);

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, sortedGroupedArticlesDescendingByYear);
    }

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
                ArticleMapper.INSTANCE.articlesToArticleDTOs(findAll());

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

    private List<Article> findAll(){
        return articleRepository.findAll();
    }
}