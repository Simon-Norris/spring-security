package com.learn.spring_security.base.service.email;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);

}
