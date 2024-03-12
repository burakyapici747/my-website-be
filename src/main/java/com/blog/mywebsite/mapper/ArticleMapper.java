package com.blog.mywebsite.mapper;

import com.blog.mywebsite.api.request.ArticlePutRequest;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTO articleToArticleDTO(Article article);

    List<ArticleDTO> articlesToArticleDTOs(List<Article> articles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void articlePutRequestToArticleDTO(ArticlePutRequest articlePutRequest, @MappingTarget Article article);
}