package com.blog.mywebsite;

import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.exception.EntityNotFoundException;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.ArticleService;
import com.blog.mywebsite.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleServiceTest {
    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @BeforeEach
    public void setupTest(){
        articleRepository = mock(ArticleRepository.class);

        articleService = new ArticleServiceImpl(articleRepository);
    }

    @Test
    void ExistingArticleInDatabase_whenGetAll_thenReturnArticleDTOList(){
        //Arrange
        Article article = new Article();
        article.setTitle("TestTitle");
        article.setContent("TestContent");
        article.setRate(1);
        article.setReadingTime(1);

        ArticleDTO articleDTO = new ArticleDTO("TestTitle", "TestContent", 1,1);

        List<Article> articleList = List.of(article);
        List<ArticleDTO> expectedArticleDTOList = List.of(articleDTO);

        when(articleRepository.findAll()).thenReturn(articleList);

        //Act
        List<ArticleDTO> actualArticleDTOList = articleService.getAll().getData();

        //Assertion
        assertNotNull(actualArticleDTOList);
        assertEquals(expectedArticleDTOList.get(0), actualArticleDTOList.get(0));

        verify(articleRepository, times(1)).findAll();
    }

    @Test
     void noArticleInDatabase_whenGetAll_thenReturnEmptyList(){
        //Arrange
        when(articleRepository.findAll()).thenReturn(Collections.emptyList());

        //Act
        List<ArticleDTO> expectedArticleDTOList = articleService.getAll().getData();

        //Assertion
        assertNotNull(expectedArticleDTOList);
        assertEquals(0, expectedArticleDTOList.size());

        verify(articleRepository, times(1)).findAll();
    }

    @Test
     void givenExistingArticleId_whenDeleteById_thenReturnBaseResponse(){
        //Arrange
        String randomUUID = UUID.randomUUID().toString();

        Article article = new Article();
        article.setId(randomUUID);
        article.setTitle("TestTitle");
        article.setContent("TestContent");
        article.setRate(1);
        article.setReadingTime(1);

        when(articleRepository.findById(randomUUID)).thenReturn(Optional.of(article));
        doNothing().when(articleRepository).delete(article);

        //Act
        BaseResponse actualBaseResponse = articleService.deleteById(randomUUID.toString());

        //Assertion
        assertNotNull(actualBaseResponse);

        verify(articleRepository, times(1)).delete(any());
        verify(articleRepository, times(1)).findById(any());

    }

    @Test
    void givenNotExistingArticleId_whenDeleteById_thenThrowEntityNotFoundException(){
        //Arrange
        String randomUUID = UUID.randomUUID().toString();

        when(articleRepository.findById(any())).thenThrow(EntityNotFoundException.class);

        //Act
        assertThrows(EntityNotFoundException.class, () -> articleService.deleteById(randomUUID.toString()));

        //Assertion
        verify(articleRepository, times(1)).findById(randomUUID);
        verify(articleRepository, times(0)).delete(any());
    }
}
