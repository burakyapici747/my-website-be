package com.blog.mywebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity {
    @NotBlank(message = "Content field cannot be empty.")
    @Size(min = 5, max = 1000, message = "Content field must be between 5 and 1000 characters long.")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Comment> subComments = new HashSet<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Set<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(Set<Comment> subComments) {
        this.subComments = subComments;
    }
}