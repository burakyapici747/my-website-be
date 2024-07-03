package com.blog.mywebsite.service.impl;

import com.blog.mywebsite.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.hibernate.internal.util.StringHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String senderEmail, String[] receiverEmails, String subject, String data) {
        emailValidations(senderEmail, receiverEmails, subject, data);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(senderEmail);
            helper.setTo(filterAndValidateReceiverEmails(receiverEmails));
            helper.setSubject(subject);
            helper.setText(generateEmailContent(data), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Failed to send email" + e.getMessage());
        }
    }

    private void emailValidations(String senderEmail, String[] receiverEmails, String subject, String content) {
        if (StringHelper.isBlank(senderEmail) || StringHelper.isBlank(subject) || StringHelper.isBlank(content) || receiverEmails == null) {
            throw new IllegalArgumentException("sendEmail parameters are invalid.");
        }
    }

    private String[] filterAndValidateReceiverEmails(String[] receiverEmails) {
        if (receiverEmails == null || receiverEmails.length == 0) {
            throw new IllegalArgumentException("Receiver email addresses must not be null or empty.");
        }
        return Arrays.stream(receiverEmails)
                .filter(email -> email != null && !email.isBlank())
                .map(String::trim)
                .toArray(String[]::new);
    }

    private String generateEmailContent(String token) {
        String url = "https://www.yourwebsite.com/verify?token=" + token;
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Verify Your Email</title>" +
                "</head>" +
                "<body>" +
                "<h2>Welcome to Our Service!</h2>" +
                "<p>Click the link below to verify your email address:</p>" +
                "<a href=\"" + url + "\">Verify Email</a>" +
                "<p>If you did not request this email, please ignore it.</p>" +
                "<br>" +
                "<p>Thank you,</p>" +
                "<p>Your Website Team</p>" +
                "</body>" +
                "</html>";
    }
}