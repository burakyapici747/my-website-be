package com.blog.mywebsite.model;

import jakarta.persistence.*;
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

    private int readingTime;

    private long rate;

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

    public void setRate(long rate) {
        this.rate = rate;
    }
}