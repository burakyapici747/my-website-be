package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//TODO: Message alanlarını ingilizce olarak değiştir
public record CommentPostRequest(
        @NotBlank(message = "Content alanı boş bırakılamaz!")
        @Size(max = 100, message = "Content max 100 karakter olabilir!")
        String content,
        @Positive(message = "Rate sadece pozitif olabilir!")
        long rate
) { }
