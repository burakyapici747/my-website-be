package com.blog.mywebsite.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//TODO: Message alanlarını ingilizce olarak değiştir!
public record ArticlePutRequest(
        @NotBlank(message = "Title boş bırakılamaz")
        @Size(max = 100, message = "Title max 100 karakter olabilir!")
        String title,

        @NotBlank(message = "Content boş bırakılamaz!")
        @Size(min = 10, max = 1000, message = "Content min 10 max 100 karakter olabilir!")
        String content,

        @Positive(message = "ReadingTime sadece pozitif olabilir!")
        int readingTime,

        @Positive(message = "Rate sadece pozitifi olabilir!")
        long rate
) { }
