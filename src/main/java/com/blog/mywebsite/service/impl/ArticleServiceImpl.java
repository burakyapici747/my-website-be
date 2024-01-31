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
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public DataResponse<List<ArticleDTO>> getAll() {
        final List<Article> articleList = articleRepository.findAll();
        final List<ArticleDTO> articleDTOList = ArticleMapper.INSTANCE.articleListToArticleDTOList(articleList);

        return new SuccessDataResponse<>(articleDTOList, EntityConstant.SUCCESS_FETCH);
    }

    @Override
    public BaseResponse deleteById(String id) {
        final Article article = findById(id);

        articleRepository.delete(article);

        return new SuccessResponse(EntityConstant.SUCCESS_DELETE);
    }

    //TODO: PostRequest boş gelebilme ihtimalinide kontrol et.
    @Override
    public DataResponse<ArticleDTO> create(ArticlePostRequest articlePostRequest) {
        Article article = new Article();

        article.setTitle(articlePostRequest.title());
        article.setContent(articlePostRequest.content());
        article.setReadingTime(articlePostRequest.readingTime());
        article.setRate(articlePostRequest.rate());

        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));

        return new SuccessDataResponse<>(articleDTO, EntityConstant.SUCCESS_CREATE);
    }

    @Override
    public DataResponse<ArticleDTO> updateById(String id, ArticlePutRequest articlePutRequest) {
        final Article article = findById(id);

        article.setTitle(articlePutRequest.title());
        article.setContent(articlePutRequest.content());
        article.setRate(articlePutRequest.rate());
        article.setReadingTime(articlePutRequest.readingTime());

        final ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDTO(articleRepository.save(article));

        return new SuccessDataResponse<>(articleDTO, EntityConstant.SUCCESS_UPDATE);
    }

    @Override
    public DataResponse<ArticleDTO> getById(String id) {
        final Article article = findById(id);
        final ArticleDTO articleDTO =  ArticleMapper.INSTANCE.articleToArticleDTO(article);

        return new SuccessDataResponse<>(articleDTO, EntityConstant.SUCCESS_FETCH);
    }

     public Article findById(String id){
        return articleRepository.findById(UUID.fromString(id))
                .orElseThrow( () -> new EntityNotFoundException(EntityConstant.ARTICLE_NOT_FOUND));
    }
}