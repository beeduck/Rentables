package com.rent.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Duck on 11/11/2016.
 */
@Configuration
@ComponentScan
public class MailConfig {

    @Autowired
    private GeneralProperties generalProperties;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(generalProperties.getEmailHost());
        javaMailSender.setProtocol(generalProperties.getEmailProtocol());
        javaMailSender.setPort(generalProperties.getEmailPort());
        javaMailSender.setUsername(generalProperties.getEmailUsername());

        return javaMailSender;
    }
}