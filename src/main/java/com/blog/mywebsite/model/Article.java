package com.blog.mywebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ARTICLE")
public class Article extends BaseEntity {
    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "article",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Comment> commentList;

    @ManyToMany(
            targetEntity = ContentType.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "ARTICLE_CONTENT_TYPE",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "content_type_id")
    )
    private List<ContentType> contentTypeList;

    private String title;

    private String content;

    @Min(0)
    private int readingTime = 0;
    @Min(0)
    private int rate = 0;

    private LocalDateTime publishDate;

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
}