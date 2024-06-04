package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.SuccessfulResponse;
import com.blog.mywebsite.api.response.SuccessfulDataResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.specification.SearchCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.blog.mywebsite.constant.ArticleConstant.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getArticles(
            SearchOperation searchOperation,
            String id,
            LocalDate publishDate,
            int rate,
            int readingTime,
            String categoryName
    ) {
        CommonSpecification<Article> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(ID, id, searchOperation));
        specification.add(new SearchCriteria(PUBLISH_DATE, publishDate, searchOperation));
        specification.add(new SearchCriteria(RATE, rate, searchOperation));
        specification.add(new SearchCriteria(READING_TIME, readingTime, searchOperation));
        specification.add(new SearchCriteria(CATEGORY_ID, categoryName, searchOperation));

        List<Article> articleList = articleRepository.findAll(specification);
        List<ArticleDTO> articleDTOList = ArticleMapper.INSTANCE.articlesToArticleDTOs(articleList);

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOList);
    }

    @Override
    public BaseResponse<Map<Integer, List<ArticleDTO>>> getGroupedArticlesByYear(
            LocalDate publishDate,
            SearchOperation searchOperation
    ) {
        CommonSpecification<Article> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(PUBLISH_DATE, publishDate, searchOperation));

        List<Article> articleList = articleRepository.findAll(specification);
        List<ArticleDTO> articleDTOList = ArticleMapper.INSTANCE.articlesToArticleDTOs(articleList);
        Map<Integer, List<ArticleDTO>> groupedByYear = articleDTOList.stream()
                .collect(
                        Collectors.groupingBy(
                                articleDTO -> articleDTO.publishDate().getYear(),
                                TreeMap::new,
                                Collectors.toList()
                        )
                );

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, groupedByYear);
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate){
        final List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(
                        articleRepository.findByPublishDateBetween(startDate, endDate)
                );

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