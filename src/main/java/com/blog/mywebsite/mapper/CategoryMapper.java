package com.blog.mywebsite.mapper;

import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.model.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    @Mapping(source = "parent.id", target = "parentId")
    CategoryDTO categoryToCategoryDTO(Category category);
    List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categoryList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void categoryPutRequestToCategoryDTO(CategoryPutRequest categoryPutRequest, @MappingTarget Category category);
}