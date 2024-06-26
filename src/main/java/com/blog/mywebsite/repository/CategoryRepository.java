package com.blog.mywebsite.repository;

import com.blog.mywebsite.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    boolean existsByName(String name);
    boolean existsById(String id);
}
