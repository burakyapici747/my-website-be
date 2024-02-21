package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByArticleId(String articleId);
}
