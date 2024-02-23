package com.blog.mywebsite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client extends BaseEntity {
    private String content;

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
}
