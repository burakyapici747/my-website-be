package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface CommentRepository extends JpaRepository<Comment, String> {
}
