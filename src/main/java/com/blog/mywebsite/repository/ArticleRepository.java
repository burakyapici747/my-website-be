package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findByPublishDate(LocalDateTime date);
    List<Article> findByPublishDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
