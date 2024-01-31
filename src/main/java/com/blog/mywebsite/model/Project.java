package com.blog.mywebsite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project extends BaseEntity{
    private String title;
    private String imagePathId;
}
