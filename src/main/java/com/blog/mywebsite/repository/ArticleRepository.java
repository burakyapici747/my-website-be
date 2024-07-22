package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    List<Article> findByPublishDate(LocalDate date);
    List<Article> findByPublishDateBetween(LocalDate startDate, LocalDate endDate);
    List<Article> findByCategoryName(String categoryName);
    boolean existsByTitle(String title);
}
