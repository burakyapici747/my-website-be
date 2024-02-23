package com.blog.mywebsite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "CONTENT_TYPE")
public class ContentType extends BaseEntity {
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
}
