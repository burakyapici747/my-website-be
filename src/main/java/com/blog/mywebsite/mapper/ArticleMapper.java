package com.blog.mywebsite.mapper;

import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTO articleToArticleDTO(Article article);
    List<ArticleDTO> articlesToArticleDTOs(List<Article> articles);
}
