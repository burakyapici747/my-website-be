package com.blog.mywebsite;


import com.blog.mywebsite.api.request.ArticlePostRequest;
import com.blog.mywebsite.api.response.BaseResponse;
import com.blog.mywebsite.dto.ArticleDTO;
import com.blog.mywebsite.model.Article;
import com.blog.mywebsite.repository.ArticleRepository;
import com.blog.mywebsite.service.impl.ArticleServiceImpl;
import com.blog.mywebsite.util.ArticleTestUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Nested
    class CreateTest {
        /*
        * given valid input then create article
        * given invalid inputs then
        * */

        @Test
        void givenNotNullInput_whenCreate_thenReturnSuccessfulDataResponse() {
            ArticlePostRequest input = ArticleTestUtil.createArticlePostRequest();
            Article article = ArticleTestUtil.createArticle();
            ArticleDTO expectedResult = ArticleTestUtil.createArticleDTO();

            when(articleRepository.save(any())).thenReturn(article);

            BaseResponse<ArticleDTO> actualResult = articleService.create(input);

            Assertions.assertNotNull(actualResult);
            Assertions.assertTrue(actualResult.isSuccess);
            Assertions.assertEquals(expectedResult, actualResult.data);

            verify(articleRepository, times(1)).save(any());
        }
    }

    @Nested
    class GetTest{
        /*
         * given valid input then create article
         * given invalid inputs then
         * */

    }
}
