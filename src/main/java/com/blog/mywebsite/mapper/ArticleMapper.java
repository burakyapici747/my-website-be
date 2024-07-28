package com.blog.mywebsite.mapper;

import com.blog.mywebsite.api.input.article.ArticlePutInput;
import com.blog.mywebsite.api.output.ArticleOutput;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);
    ArticleDTO articleToArticleDTO(Article article);
    List<ArticleDTO> articlesToArticleDTOs(List<Article> articles);
    @Mapping(target = "type", constant = "article")
    @Mapping(target = "attributes", source = ".")
    ArticleOutput articleDTOToArticleOutput(ArticleDTO articleDTO);
    @Mapping(target = "type", constant = "article")
    @Mapping(target = "attributes", source = ".")
    List<ArticleOutput> articleDTOListToArticleOutputList(List<ArticleDTO> articleDTOList);
    ArticleOutput.Attributes toAttributes(ArticleDTO articleDTO);
    Map<Integer, List<ArticleOutput>> toArticleOutputMap(Map<Integer, List<ArticleDTO>> articleDTOMap);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "publishDate", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void articlePutRequestToArticleDTO(ArticlePutInput articlePutInput, @MappingTarget Article article);
}