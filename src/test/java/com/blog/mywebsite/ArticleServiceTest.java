package com.blog.mywebsite;

import com.blog.mywebsite.api.input.article.ArticlePostInput;
import com.blog.mywebsite.api.input.article.ArticlePutInput;
import com.blog.mywebsite.constant.ArticleConstant;
import com.blog.mywebsite.constant.ArticleTestConstant;
import com.blog.mywebsite.constant.CategoryTestConstant;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.dto.CommentDTO;
import com.blog.mywebsite.enumerator.SearchOperation;
import com.blog.mywebsite.exception.EntityExistException;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.mapper.ArticleMapper;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.impl.ArticleServiceImpl;
import com.blog.mywebsite.service.impl.CategoryServiceImpl;
import com.blog.mywebsite.specification.CommonSpecification;
import com.blog.mywebsite.util.ArticleTestUtil;
import com.blog.mywebsite.util.CommentTestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    CommonSpecification<Article> articleCommonSpecification;

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Nested
    class GetTest{
        //givenAllInput_whenGetArticles_thenReturnArticleDTOList

        //givenAllInput_whenGetGroupedArticlesByYear_thenReturnArticlesByYearMap

        //givenAllInput_whenGetGroupedYearByCategoryName_thenReturnArticlesByYearMap
        //givenNotExistCategoryName_whenGetGroupedYearByCategoryName_thenReturnEmptyArticlesByYearMap

        //givenValidInput_whenGetComments_thenReturnCommentDTOList
        //givenNotExistId_whenGetComments_thenThrowEntityNotFoundException

        @ParameterizedTest
        @MethodSource("generateArgsGetArticles")
        void givenAllInput_whenGetArticles_thenReturnArticleDTOList(
                String id,
                String title,
                LocalDate publishDate,
                Integer readingTime
        ){
            List<Article> articleList = List.of(ArticleTestUtil.createArticle());
            List<ArticleDTO> expectedResult = ArticleMapper.INSTANCE
                    .articlesToArticleDTOs(articleList);
            when(articleRepository.findAll(any(Specification.class))).thenReturn(articleList);

            List<ArticleDTO> actualResult = articleService.getArticles(id, title, publishDate, readingTime);

            Assertions.assertNotNull(actualResult);
            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findAll(any(Specification.class));
        }

        @ParameterizedTest
        @MethodSource("generateArgsGetGroupedArticlesByYear")
        void givenAllInput_whenGetGroupedArticlesByYear_thenReturnArticlesByYearMap(
                LocalDate publishDate,
                SearchOperation searchOperation
        ) {
            List<Article> articleList = List.of(ArticleTestUtil.createArticle());
            Map<Integer, List<ArticleDTO>> expectedResult = ArticleTestUtil.createArticleDTOListByYearMap();

            when(articleRepository.findAll(any(Specification.class))).thenReturn(articleList);

            Map<Integer, List<ArticleDTO>> actualResult =
                    articleService.getGroupedArticlesByYear(publishDate, searchOperation);

            Assertions.assertNotNull(actualResult);
            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void givenAllInput_whenGetGroupedYearByCategoryName_thenReturnArticlesByYearMap(){
            String categoryName = CategoryTestConstant.NAME;
            List<Article> articleList = List.of(ArticleTestUtil.createArticle());
            Map<Integer, List<ArticleDTO>> expectedResult = ArticleTestUtil.createArticleDTOListByYearMap();

            when(articleRepository.findByCategoryName(categoryName)).thenReturn(articleList);

            Map<Integer, List<ArticleDTO>> actualResult = articleService.getGroupedYearByCategoryName(categoryName);

            Assertions.assertNotNull(actualResult);
            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findByCategoryName(categoryName);
        }

        @Test
        void givenNotExistCategoryName_whenGetGroupedYearByCategoryName_thenReturnEmptyArticlesByYearMap(){
            Map<Integer, List<ArticleDTO>> expectedResult = ArticleTestUtil.createEmptyArticleDTOListByYearMap();

            when(articleRepository.findByCategoryName(any())).thenReturn(Collections.emptyList());

            Map<Integer, List<ArticleDTO>> actualResult = articleService.getGroupedYearByCategoryName(any());

            Assertions.assertNotNull(actualResult);
            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findByCategoryName(any());
        }

        @Test
        void givenValidInput_whenGetComments_thenReturnCommentDTOList(){
            String id = ArticleTestConstant.ID;
            Article article = ArticleTestUtil.createArticle();
            article.getComments().add(CommentTestUtil.createComment());
            List<CommentDTO> expectedResult = CommentTestUtil.createCommentDTOList();

            when(articleRepository.findById(id)).thenReturn(Optional.of(article));

            List<CommentDTO> actualResult = articleService.getComments(id);

            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findById(id);
        }

        @Test
        void givenNotExistId_whenGetComments_thenThrowEntityNotFoundException(){
            String id = UUID.randomUUID().toString();
            when(articleRepository.findById(id)).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () -> articleService.getComments(id));

            verify(articleRepository, times(1)).findById(id);
        }

        private static Stream<Arguments> generateArgsGetArticles(){
            return Stream.of(
                    Arguments.arguments(ArticleConstant.ID, null, null, null),
                    Arguments.arguments(null, ArticleConstant.TITLE, null, null),
                    Arguments.arguments(null, null, ArticleTestConstant.PUBLISH_DATE, null),
                    Arguments.arguments(null, null, null, ArticleTestConstant.READING_TIME),
                    Arguments.arguments(null, null, null, null)
            );
        }

        private static Stream<Arguments> generateArgsGetGroupedArticlesByYear(){
            return Stream.of(
                    Arguments.arguments(ArticleTestConstant.PUBLISH_DATE_IN_DATE, SearchOperation.GREATER_THAN),
                    Arguments.arguments(null, SearchOperation.GREATER_THAN),
                    Arguments.arguments(null, null)
            );
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
            String id = UUID.randomUUID().toString();
            Article article = ArticleTestUtil.createArticle();
            when(articleRepository.findById(any())).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () -> articleService.deleteById(id));

            verify(articleRepository, times(1)).findById(id);
            verify(articleRepository, times(0)).delete(article);
        }
    }

    @Nested
    class UpdateTest{
        //givenValidInput_whenUpdateById_thenReturnArticleDTO
        //givenNotExistId_whenUpdateById_thenThrowEntityNotFoundException
        //givenExistTitle_whenUpdateById_thenThrowEntityExistException

        @Test
        void givenValidInput_whenUpdateById_thenReturnArticleDTO(){
            String id = ArticleTestConstant.ID;
            Article article = ArticleTestUtil.createArticle();
            ArticlePutInput ArticlePutInput = ArticleTestUtil.createArticlePutInput();
            ArticleDTO expectedResult = ArticleTestUtil.createArticleDTO();

            when(articleRepository.findById(id)).thenReturn(Optional.of(article));
            when(articleRepository.save(article)).thenReturn(article);

            ArticleDTO actualResult = articleService.updateById(id, ArticlePutInput);

            Assertions.assertEquals(expectedResult, actualResult);

            verify(articleRepository, times(1)).findById(id);
            verify(articleRepository, times(1)).save(article);
        }

        @Test
        void givenNotExistId_whenUpdateById_thenThrowEntityNotFoundException(){
            String id = UUID.randomUUID().toString();
            ArticlePutInput articlePutInput = ArticleTestUtil.createArticlePutInput();

            when(articleRepository.findById(id)).thenThrow(EntityNotFoundException.class);

            Assertions.assertThrows(EntityNotFoundException.class, () -> articleService.updateById(id, articlePutInput));

            verify(articleRepository, times(1)).findById(id);
            verify(articleRepository, times(0)).save(any());
        }

        @Test
        void givenExistTitle_whenUpdateById_thenThrowEntityExistException(){
            String id = ArticleTestConstant.ID;
            Article article = ArticleTestUtil.createArticle();
            ArticlePutInput articlePutInput = ArticleTestUtil.createArticlePutInput();

            when(articleRepository.findById(id)).thenReturn(Optional.of(article));
            when(articleRepository.existsByTitle(articlePutInput.title())).thenThrow(EntityExistException.class);

            Assertions.assertThrows(EntityExistException.class, () -> articleService.updateById(id, articlePutInput));

            verify(articleRepository, times(1)).findById(id);
            verify(articleRepository, times(0)).save(any());
        }
    }
}