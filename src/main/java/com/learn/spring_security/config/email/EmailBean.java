package com.learn.spring_security.config.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Profile({"dev", "prod"})
@EnableConfigurationProperties(MailProperties.class)
public class EmailBean {

    private static final Logger logger = LoggerFactory.getLogger(EmailBean.class);

    private final MailProperties mailProperties;

    @Autowired
    public EmailBean(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }


    @Bean
    public JavaMailSenderImpl mailSender() {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailProperties.getHost());
            mailSender.setPort(mailProperties.getPort());
            mailSender.setUsername(mailProperties.getUsername());
            mailSender.setPassword(mailProperties.getPassword());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.host", mailProperties.getHost());
            props.put("mail.smtp.socketFactory.port", mailProperties.getPort());
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            mailSender.setJavaMailProperties(props);

            logger.info("::: EMAIL CONFIGURATION DONE :::");
            return mailSender;
        } catch (Exception e) {
            logger.error("::: EMAIL CONFIGURATION ERROR DUE TO {} :::", e.getMessage());
            return null;
        }
    }
}
