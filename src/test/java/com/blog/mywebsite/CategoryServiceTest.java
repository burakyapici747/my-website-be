package com.blog.mywebsite;

import com.blog.mywebsite.api.request.CategoryPostRequest;
import com.blog.mywebsite.constant.CategoryTestConstant;
import com.blog.mywebsite.dto.CategoryDTO;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.model.Category;
import com.blog.mywebsite.repository.CategoryRepository;
import com.blog.mywebsite.service.impl.CategoryServiceImpl;
import com.blog.mywebsite.util.CategoryTestUtil;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith({SpringExtension.class})
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Nested
    class CreateTest{
        @Test
        void givenValidInputAndNotExistingName_whenCreate_thenReturnSuccessfulDataResponse(){
            CategoryPostRequest input = CategoryTestUtil.createCategoryPostRequest();
            Category category = CategoryTestUtil.createCategory();
            CategoryDTO expectedResult = CategoryTestUtil.createCategoryDTO();

            when(categoryRepository.existsByName(input.name())).thenReturn(false);
            when(categoryRepository.save(any())).thenReturn(category);

            BaseResponse<CategoryDTO> actualResult = categoryService.create(input);

            Assertions.assertNotNull(actualResult);
            Assertions.assertTrue(actualResult.isSuccess);
            Assertions.assertEquals(expectedResult, actualResult.data);

            verify(categoryRepository, times(1)).save(any());
        }

        @Test
        void givenValidInputAndAlreadyExistingName_whenCreate_thenReturnEntityExistException(){
            CategoryPostRequest input = CategoryTestUtil.createCategoryPostRequest();

            when(categoryRepository.existsByName(input.name())).thenReturn(true);

            Assertions.assertThrows(EntityExistException.class, () -> categoryService.create(input));

            verify(categoryRepository, times(0)).save(any());
        }

        @Test
        void givenNullInput_whenCreate_thenThrowIllegalArgumentException(){
            CategoryPostRequest input = null;

            Assertions.assertThrows(IllegalArgumentException.class, () -> categoryService.create(input));

            verify(categoryRepository, times(0)).existsByName(any());
            verify(categoryRepository, times(0)).save(any());
        }

        @Test
        void givenInvalidInput_whenCreate_thenThrowConstraintViolationException(){
            CategoryPostRequest input = CategoryTestUtil.createInvalidCategoryPostRequestWithInvalidSizeParentId();

            Assertions.assertThrows(ConstraintViolationException.class, () -> categoryService.create(input));

            verify(categoryRepository, times(0)).existsByName(any());
            verify(categoryRepository, times(0)).save(any());
        }

        @Test
        void givenValidInputAndNotNullParentId_whenCreate_thenReturnSuccessfulDataResponse(){
            CategoryPostRequest input = CategoryTestUtil.categoryPostRequestWithParentId();
            Category parentCategory = CategoryTestUtil.createParentCategory();
            Category category = CategoryTestUtil.createCategory();
            category.setParent(parentCategory);
            CategoryDTO expectedResult = CategoryTestUtil.createCategoryDTOWithParentId();

            when(categoryRepository.findById(input.parentId())).thenReturn(Optional.of(parentCategory));
            when(categoryRepository.existsByName(input.name())).thenReturn(false);
            when(categoryRepository.save(any())).thenReturn(category);

            BaseResponse<CategoryDTO> actualResult = categoryService.create(input);

            Assertions.assertNotNull(actualResult);
            Assertions.assertTrue(actualResult.isSuccess);
            Assertions.assertEquals(expectedResult, actualResult.data);

            verify(categoryRepository, times(1)).findById(input.parentId());
            verify(categoryRepository, times(1)).existsByName(input.name());
            verify(categoryRepository, times(1)).save(any());
        }
    }

    @Nested
    class DeleteTest{
        /*
        * givenNullInput_whenDelete_thenThrowIllegalArgumentException
        * givenNotNullAndExistId_whenDelete_thenReturnSuccessfulResponseDataResponse
        * givenNotNullAndNotExistId_whenDelete_thenThrowEntityNotFoundException
        * givenInvalidInput_whenDelete_thenThrowConstraintViolationException
        * */
        @Test
        void givenNullInput_whenDelete_thenThrowIllegalArgumentException(){
            String input = null;
            Assertions.assertThrows(IllegalArgumentException.class, () -> categoryService.deleteById(input));
            verify(categoryRepository, times(0)).delete((Category) any());
        }

        @Test
        void givenNotNullAndExistId_whenDelete_thenReturnSuccessfulResponseDataResponse(){
            String input = CategoryTestConstant.ID;
            Category category = CategoryTestUtil.createCategory();

            when(categoryRepository.findById(input)).thenReturn(Optional.of(category));
            doNothing().when(categoryRepository).delete(category);

            categoryService.deleteById(input);

            verify(categoryRepository, times(1)).delete(category);
            verify(categoryRepository, times(1)).findById(input);
        }

        @Test
        void givenNotNullAndNotExistId_whenDelete_thenThrowEntityNotFoundException(){
            String input = CategoryTestConstant.ID;

            when(categoryRepository.findById(input)).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(input));

            verify(categoryRepository, times(1)).findById(input);
            verify(categoryRepository, times(0)).delete((Category) any());
        }

        @Test
        void givenInvalidInput_whenDelete_thenThrowConstraintViolationException(){
        }
    }
}