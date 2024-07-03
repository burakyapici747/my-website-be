package com.blog.mywebsite.config;

import com.blog.mywebsite.api.CustomBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApplicationConfig {
    @Bean
    public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }

    @Bean
    public CustomBeanPostProcessor customBeanPostProcessor(){
        return new CustomBeanPostProcessor();
    }
}
