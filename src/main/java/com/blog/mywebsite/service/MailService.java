package com.blog.mywebsite.service;

public interface MailService {
    void sendEmail(String senderEmail, String[] receiverEmails, String subject, String content);
}