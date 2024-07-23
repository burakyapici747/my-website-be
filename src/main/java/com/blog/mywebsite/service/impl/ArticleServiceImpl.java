package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.input.article.ArticlePostInput;
import com.blog.mywebsite.api.input.article.ArticlePutInput;
import com.blog.mywebsite.common.util.ValueUtil;
import com.blog.mywebsite.constant.DateConstant;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.model.Comment;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.service.CategoryService;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.specification.SearchCriteria;
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
    public List<ArticleDTO> getArticles(String id, String title, LocalDate publishDate, Integer readingTime) {
        CommonSpecification<Article> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(ID, id, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(TITLE, title, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(PUBLISH_DATE, publishDate, SearchOperation.EQUAL));
        specification.add(new SearchCriteria(READING_TIME, readingTime, SearchOperation.EQUAL));

        List<Article> articleList = articleRepository.findAll(specification);
        return ArticleMapper.INSTANCE.articlesToArticleDTOs(articleList);
    }

    @Override
    public Map<Integer, List<ArticleDTO>> getGroupedArticlesByYear(LocalDate publishDate, SearchOperation searchOperation) {
        CommonSpecification<Article> specification = new CommonSpecification<>();
        specification.add(new SearchCriteria(PUBLISH_DATE, publishDate, (Objects.nonNull(searchOperation)) ? searchOperation : SearchOperation.EQUAL));

        List<Article> articleList = articleRepository.findAll(specification);
        List<ArticleDTO> articleDTOList = ArticleMapper.INSTANCE.articlesToArticleDTOs(articleList);
        return getArticlesGroupedByYearSorted(articleDTOList);
    }

    @Override
    public Map<Integer, List<ArticleDTO>> getGroupedYearByCategoryName(String categoryName) {
        List<Article> articleList = articleRepository.findByCategoryName(categoryName);
        List<ArticleDTO> articleDTOList = ArticleMapper.INSTANCE.articlesToArticleDTOs(articleList);
        return getArticlesGroupedByYearSorted(articleDTOList);
    }

    @Override
    public List<ArticleDTO> getByDateRange(LocalDate startDate, LocalDate endDate){
        startDate = Optional.ofNullable(startDate).orElse(LocalDate.of(DateConstant.START_YEAR, DateConstant.START_MONTH, DateConstant.START_DAY));
        endDate = Optional.ofNullable(endDate).orElse(LocalDate.now());
        return ArticleMapper.INSTANCE.articlesToArticleDTOs(articleRepository.findByPublishDateBetween(startDate, endDate));
    }

    @Override
    public List<CommentDTO> getComments(String id){
        List<Comment> commentList = findById(id).getComments();
        return CommentMapper.INSTANCE.commentsToCommentDTOs(commentList);
    }

    @Override
    public ArticleDTO create(ArticlePostInput articlePostInput) {
        ValueUtil.checkDataIsNull(articlePostInput, "ArticlePostRequest is can not be null.");
        checkTitleIsExistByTitle(articlePostInput.title());

        Article article = new Article();
        checkCategoryExistAndSetArticleCategoryName(articlePostInput.categoryId(), article);
        article.setTitle(articlePostInput.title());
        article.setContent(articlePostInput.content());
        article.setReadingTime(articlePostInput.readingTime());
        article.setRate(0);
        article.setPublishDate(LocalDate.parse(articlePostInput.publishDate()));
        return ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));
    }

    @Override
    public ArticleDTO updateById(String id, ArticlePutInput articlePutInput) {
        Article article = findById(id);
        checkTitleIsExistByTitle(articlePutInput.title());
        ArticleMapper.INSTANCE.articlePutRequestToArticleDTO(articlePutInput, article);
        return ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));
    }

    @Override
    public ArticleDTO deleteById(String id) {
        Article article = findById(id);
        articleRepository.delete(article);
        return ArticleMapper.INSTANCE.articleToArticleDTO(article);
    }

    public Article findById(String id){
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityConstant.ARTICLE_NOT_FOUND));
    }

    private void checkCategoryExistAndSetArticleCategoryName(String categoryId, Article article){
        if(Objects.nonNull(categoryId)){
            article.setCategory(categoryService.findById(categoryId));
        }
    }

    private void checkTitleIsExistByTitle(String title){
        if(articleRepository.existsByTitle(title)){
            throw new EntityExistException("An article with the same title already exists. Please choose a different title.");
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