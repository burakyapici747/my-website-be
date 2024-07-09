package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.api.request.CategoryPutRequest;
import com.blog.mywebsite.common.util.ValueUtil;
import com.blog.mywebsite.common.validator.CommonValidator;
import com.blog.mywebsite.constant.EntityConstant;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CategoryMapper;
import com.blog.mywebsite.model.Category;
import com.blog.mywebsite.repository.CategoryRepository;
import com.blog.mywebsite.service.CategoryService;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.specification.SearchCriteria;
import org.springframework.stereotype.Service;

import static com.blog.mywebsite.constant.CategoryConstant.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getCategories(String id, String parentId, String name) {
        CommonSpecification<Category> commonSpecification = new CommonSpecification<>();
        commonSpecification.add(new SearchCriteria(ID, id, SearchOperation.EQUAL));
        commonSpecification.add(new SearchCriteria(PARENT_ID, parentId, SearchOperation.EQUAL));
        commonSpecification.add(new SearchCriteria(NAME, name, SearchOperation.EQUAL));
        return CategoryMapper.INSTANCE.categoriesToCategoryDTOs(categoryRepository.findAll(commonSpecification));
    }

    @Override
    public Map<String, List<CategoryDTO>> getGroupedCategoriesWithSubcategories(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = CategoryMapper.INSTANCE.categoriesToCategoryDTOs(categoryList);

        return categoryDTOList.stream()
            .collect(Collectors.groupingBy(
                    CategoryDTO::name,
                    TreeMap::new,
                    Collectors.toList()
            ));
    }

    @Override
    public CategoryDTO deleteById(String id) {
        ValueUtil.checkDataIsNull(id, "Id is cannot be null");
        Category category = findById(id);
        categoryRepository.delete(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Override
    public CategoryDTO create(CategoryPostRequest categoryPostRequest) {
        ValueUtil.checkDataIsNull(categoryPostRequest, "CategoryPostRequest cannot be null");
        CommonValidator.validateInput(categoryPostRequest);
        checkCategoryIsExistByName(categoryPostRequest.name());

        Category category = new Category();
        category.setName(categoryPostRequest.name());
        checkParentCategoryExistThenSetParentCategory(categoryPostRequest.parentId(), category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateById(String id, CategoryPutRequest categoryPutRequest){
        CommonValidator.validateInput(categoryPutRequest);
        ValueUtil.checkDataIsNull(categoryPutRequest, "CategoryPutRequest cannot be null");
        Category category = findById(id);
        checkParentCategoryExistThenSetParentCategory(categoryPutRequest.parentId(), category);
        CategoryMapper.INSTANCE.categoryPutRequestToCategoryDTO(categoryPutRequest, category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(categoryRepository.save(category));
    }

    public Category findById(String id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityConstant.NOT_FOUND_DATA));
    }

    private void checkCategoryIsExistByName(String name){
        if(categoryRepository.existsByName(name)){
                throw new EntityExistException("This category name is already in use.");
        }
    }

    private void checkParentCategoryExistThenSetParentCategory(String parentId, Category category){
        if(Objects.nonNull(parentId)){
            category.setParent(findById(parentId));
        }
    }
}