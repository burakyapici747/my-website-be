package com.blog.mywebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ARTICLE")
public class Article extends BaseEntity {
    @OneToMany
    @JoinTable(name = "ARTICLE_COMMENT")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ARTICLE_CONTENT_TYPE"
    )
    private List<ContentType> contentTypes;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 5, max = 255, message = "Title field must be between 5 and 255 characters long.")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    @Size(min = 5, max = 1000, message = "Content field must be between 5 and 2500 characters long.")
    private String content;

    @PositiveOrZero(message = "Reading time field cannot be negative.")
    private int readingTime;

    @PositiveOrZero(message = "Rate field cannot be negative.")
    private int rate = 0;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime publishDate;

    @PrePersist
    public void initial(){
        if(Objects.isNull(this.publishDate)){
            this.publishDate = LocalDateTime.now();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadingTime() {
        return this.readingTime;
    }

    public void setReadingTime(int readingTime) {
        this.readingTime = readingTime;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public LocalDateTime getPublishDate(){
        return this.publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate){
        this.publishDate = publishDate;
    }
}