package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.api.response.SuccessfulDataResponse;
import com.blog.mywebsite.api.response.SuccessfulResponse;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CategoryMapper;
import com.blog.mywebsite.model.Category;
import com.blog.mywebsite.repository.CategoryRepository;
import com.blog.mywebsite.service.CategoryService;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.specification.SearchCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.blog.mywebsite.constant.CategoryConstant.*;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BaseResponse<List<CategoryDTO>> getAll() {
        final List<CategoryDTO> categoryDTOList =
                CategoryMapper.INSTANCE.categoriesToCategoryDTOs(categoryRepository.findAll());

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, categoryDTOList);
    }

    @Override
    public BaseResponse<List<CategoryDTO>> getCategories(String id, String parentId, String name) {
        CommonSpecification<Category> commonSpecification = new CommonSpecification<>();
        commonSpecification.add(new SearchCriteria(ID, id, SearchOperation.EQUAL));
        commonSpecification.add(new SearchCriteria(PARENT_ID, parentId, SearchOperation.EQUAL));
        commonSpecification.add(new SearchCriteria(NAME, name, SearchOperation.EQUAL));

        final List<CategoryDTO> categoryDTOList =
                CategoryMapper.INSTANCE.categoriesToCategoryDTOs(categoryRepository.findAll(commonSpecification));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_FETCH, categoryDTOList);
    }

    @Override
    public BaseResponse<Void> deleteById(String id) {
        final Category category = findById(id);

        categoryRepository.delete(category);

        return new SuccessfulResponse(HttpStatus.OK.value(), EntityConstant.SUCCESS_DELETE);
    }

    @Override
    public BaseResponse<CategoryDTO> create(CategoryPostRequest categoryPostRequest) {
        final Category category = new Category();

        categoryPostRequest.parentId().ifPresent(parentId-> category.setParent(findById(parentId)));
        category.setName(categoryPostRequest.name());

        final CategoryDTO categoryDTO =
                CategoryMapper.INSTANCE.categoryToCategoryDTO(categoryRepository.save(category));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_CREATE, categoryDTO);
    }

    @Override
    public BaseResponse<CategoryDTO> updateById(String id, CategoryPutRequest categoryPutRequest) {
        final Category category = findById(id);

        CategoryMapper.INSTANCE.categoryPutRequestToCategoryDTO(categoryPutRequest, category);

        final CategoryDTO categoryDTO = CategoryMapper.INSTANCE.categoryToCategoryDTO(categoryRepository.save(category));

        return new SuccessfulDataResponse<>(HttpStatus.OK.value(), EntityConstant.SUCCESS_UPDATE, categoryDTO);
    }

    private Category findById(String id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }
}