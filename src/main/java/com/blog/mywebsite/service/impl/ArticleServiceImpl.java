package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.SuccessfulResponse;
import com.blog.mywebsite.api.response.SuccessfulDataResponse;
import com.blog.mywebsite.common.util.ValueUtil;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.service.CategoryService;
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
    private final CategoryService categoryService;
    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryService categoryService) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getArticles(
            String id,
            LocalDate publishDate,
            int rate,
            int readingTime,
            String categoryName
    ) {
        CommonSpecification<Article> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(ID, id, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(PUBLISH_DATE, publishDate, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(RATE, rate, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(READING_TIME, readingTime, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(CATEGORY_ID, categoryName, SearchOperation.EQUAL));

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
        Map<Integer, List<ArticleDTO>> groupedByYear = getArticlesGroupedByYearSorted(articleDTOList);
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, groupedByYear);
    }

    @Override
    public BaseResponse<Map<Integer, List<ArticleDTO>>> getGroupedYearByCategoryName(String categoryName) {
        List<Article> articleList = articleRepository.findByCategoryName(categoryName);
        List<ArticleDTO> articleDTOList = ArticleMapper.INSTANCE.articlesToArticleDTOs(articleList);
        Map<Integer, List<ArticleDTO>> groupedByYear = getArticlesGroupedByYearSorted(articleDTOList);
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, groupedByYear);
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getByDateRange(LocalDate startDate, LocalDate endDate){
        List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(
                        articleRepository.findByPublishDateBetween(startDate, endDate)
                );

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOs);
    }

    @Override
    public BaseResponse<List<ArticleDTO>> getAll() {
        List<ArticleDTO> articleDTOs =
                ArticleMapper.INSTANCE.articlesToArticleDTOs(findAll());
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, articleDTOs);
    }

    @Override
    public BaseResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest) {
        ValueUtil.checkDataIsNull(articlePostRequest, "ArticlePostRequest is can not be null.");

        Article article = new Article();
        article.setTitle(articlePostRequest.title());
        article.setContent(articlePostRequest.content());
        article.setReadingTime(articlePostRequest.readingTime());
        article.setRate(0);
        article.setPublishDate(LocalDate.parse(articlePostRequest.publishDate()));
        checkCategoryExistAndSetArticleCategoryName(articlePostRequest.categoryId(), article);

        ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_CREATE, articleDTO);
    }

    @Override
    public BaseResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest) {
        Article article = findById(id);

        ArticleMapper.INSTANCE.articlePutRequestToArticleDTO(articlePutRequest, article);

        ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));
        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_UPDATE, articleDTO);
    }

    @Override
    public BaseResponse<Void> deleteById(String id) {
        Article article = findById(id);

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

    private void checkCategoryExistAndSetArticleCategoryName(String categoryId, Article article){
        if(Objects.nonNull(categoryId)){
            article.setCategory(categoryService.findById(categoryId));
        }
    }

    private Map<Integer, List<ArticleDTO>> getArticlesGroupedByYearSorted(List<ArticleDTO> articleDTOList){
        return articleDTOList.stream()
                .collect(Collectors.groupingBy(
                        articleDTO -> articleDTO.publishDate().getYear(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }
}