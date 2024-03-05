package com.blog.mywebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity {
    @NotBlank(message = "Content field cannot be empty.")
    @Size(min = 5, max = 1000, message = "Content field must be between 5 and 1000 characters long.")
    private String content;

    @Positive(message = "Rate field cannot be negative.")
    private long rate = 0;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }
}