package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
