package com.blog.mywebsite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.engine.internal.ImmutableEntityEntry;

import java.util.List;

@Entity
@Table
public class ContentType extends BaseEntity {
    @ManyToMany(
            mappedBy = "contentTypeList",
            targetEntity = Article.class
    )
    private List<Article> articleList;

    @NotBlank(message = "Title field cannot be empty.")
    @Size(min = 5, max = 255, message = "Title field must be between 5 and 255 characters long.")
    private String title;

    @NotBlank(message = "Description field cannot be empty.")
    @Size(min = 5, max = 500, message = "Title field must be between 5 and 500 characters long.")
    private String description;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
