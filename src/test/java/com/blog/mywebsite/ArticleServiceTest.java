package com.blog.mywebsite;

import com.blog.mywebsite.api.input.article.ArticlePostInput;
import com.blog.mywebsite.constant.ArticleTestConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.CommentMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.model.Comment;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.impl.ArticleServiceImpl;
import com.blog.mywebsite.service.impl.CategoryServiceImpl;
import com.blog.mywebsite.util.ArticleTestUtil;
import com.blog.mywebsite.util.CommentTestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Nested
    class GetTest{
        //givenValidInput_whenGetArticles_thenReturnArticleDTOList
        //givenNoParameters_whenGetArticles_thenReturnArticleDTOList

        //givenInvalidInput_whenGetComments_thenReturnCommentDTOList

        @Test
        void givenInvalidInput_whenGetComments_thenReturnCommentDTOList(){
            String id = ArticleTestConstant.ID;
            Article article = ArticleTestUtil.createArticle();
            List<CommentDTO> expectedResult = CommentTestUtil.createCommentDTOList();

            when(articleRepository.findById(id)).thenReturn(Optional.of(article));

            List<CommentDTO> actualResult = articleService.getComments(id);

            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findById(id);
        }
    }

    @Nested
    class CreateTest{
        //givenValidInput_whenCreate_thenReturnArticleDTO
        //givenExistTitle_whenCreate_thenThrowEntityExistException
        //givenNullInput_whenCreate_thenThrowIllegalArgumentException
        //givenInvalidCategoryId_whenCreate_thenThrowEntityNotFoundException

        //Controller Validation Test
        //givenCategoryIdIsNull_whenCreate_thenThrowMethodArgumentNotValidException
        //givenCategoryIdIsInvalid_whenCreate_thenThrowMethodArgumentNotValidException

        @Test
        void givenValidInput_whenCreate_thenReturnArticleDTO(){
            ArticlePostInput articlePostInput = ArticleTestUtil.createArticlePostInput();
            Article article = ArticleTestUtil.createArticle();
            ArticleDTO expectedResult = ArticleTestUtil.createArticleDTO();

            when(articleRepository.existsByTitle(articlePostInput.title())).thenReturn(false);
            when(articleRepository.save(any())).thenReturn(article);

            ArticleDTO actualResult = articleService.create(articlePostInput);

            Assertions.assertNotNull(actualResult);
            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).existsByTitle(articlePostInput.title());
            verify(articleRepository, times(1)).save(any());
        }

        @Test
        void givenExistTitle_whenCreate_thenThrowEntityExistException(){
            ArticlePostInput articlePostInput = ArticleTestUtil.createArticlePostInput();

            when(articleRepository.existsByTitle(articlePostInput.title())).thenReturn(true);

            Assertions.assertThrows(EntityExistException.class, () -> articleService.create(articlePostInput));

            verify(articleRepository, times(1)).existsByTitle(articlePostInput.title());
            verify(articleRepository, times(0)).save(any());
        }

        @Test
        void givenNullInput_whenCreate_thenThrowIllegalArgumentException(){
            Assertions.assertThrows(IllegalArgumentException.class, () -> articleService.create(null));

            verify(articleRepository, times(0)).existsByTitle(any());
            verify(articleRepository, times(0)).findById(any());
            verify(articleRepository, times(0)).save(any());
        }

        @Test
        void givenInvalidInput_whenCreate_thenThrowEntityNotFoundException(){
            ArticlePostInput articlePostInput = ArticleTestUtil.createArticlePostInput();

            when(articleRepository.existsByTitle(any())).thenReturn(false);
            when(categoryService.findById(any())).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () -> articleService.create(articlePostInput));

            verify(articleRepository, times(1)).existsByTitle(any());
            verify(categoryService, times(1)).findById(any());
            verify(articleRepository, times(0)).save(any());
        }
    }

    @Nested
    class DeleteTest{
        //givenValidInput_whenDelete_thenReturnArticleDTO
        //givenInvalidValidInput_whenDelete_thenThrowEntityNotFoundException

        @Test
        void givenValidInput_whenDeleteById_thenReturnArticleDTO(){
            String id = ArticleTestConstant.ID;
            Article article = ArticleTestUtil.createArticle();
            ArticleDTO expectedResult = ArticleTestUtil.createArticleDTO();

            when(articleRepository.findById(id)).thenReturn(Optional.of(article));
            doNothing().when(articleRepository).delete(article);

            ArticleDTO actualResult = articleService.deleteById(id);

            Assertions.assertNotNull(actualResult);
            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findById(id);
            verify(articleRepository, times(1)).delete(article);
        }

        @Test
        void givenInvalidValidInput_whenDelete_thenThrowEntityNotFoundException(){
            when(articleRepository.findById(any())).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () -> articleService.deleteById(any()));

            verify(articleRepository, times(1)).findById(any());
            verify(articleRepository, times(0)).delete((Article) any());
        }
    }
}