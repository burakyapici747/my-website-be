package com.blog.mywebsite.model;

import jakarta.persistence.*;

@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity {

    @ManyToOne(targetEntity = Article.class)
    @JoinColumn(
            name = "article_id",
            referencedColumnName = "id"
    )
    private Article article;

    private String content;
    private long rate;

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
