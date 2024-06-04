package com.blog.mywebsite.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category extends BaseEntity{
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "category"
    )
    private List<Article> articles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "parent"
    )
    private Set<Category> subCategories = new HashSet<>();

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> categories) {
        this.subCategories = categories;
    }
}