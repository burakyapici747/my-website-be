package com.blog.mywebsite.mapper;

import com.blog.mywebsite.api.output.CategoryOutput;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.model.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    @Mapping(source = "parent.id", target = "parentId")
    CategoryDTO categoryToCategoryDTO(Category category);
    List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categoryList);
    @Mapping(target = "type", constant = "category")
    @Mapping(target = "attributes", source = ".")
    CategoryOutput categoryDTOToCategoryOutput(CategoryDTO categoryDTO);

    @Mapping(target = "type", constant = "category")
    @Mapping(target = "attributes", source = ".")
    List<CategoryOutput> categoryDTOListToCategoryOutputList(List<CategoryDTO> categoryDTOList);
    CategoryOutput.Attributes toAttributes(CategoryDTO categoryDTO);
    Map<Integer, List<CategoryOutput>> toCategoryOutputMap(Map<Integer, List<CategoryDTO>> categoryDTOMap);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void categoryPutRequestToCategoryDTO(CategoryPutRequest categoryPutRequest, @MappingTarget Category category);
}