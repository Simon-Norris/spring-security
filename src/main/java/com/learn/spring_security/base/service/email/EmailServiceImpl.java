package com.learn.spring_security.base.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSenderImpl mailSender;

    public EmailServiceImpl(JavaMailSenderImpl javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(mailSender.getUsername());

        mailSender.send(message);

    }
}
