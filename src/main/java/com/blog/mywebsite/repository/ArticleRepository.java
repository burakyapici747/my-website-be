package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findByPublishDate(LocalDate date);
    List<Article> findByPublishDateBetween(LocalDate startDate, LocalDate endDate);
}
