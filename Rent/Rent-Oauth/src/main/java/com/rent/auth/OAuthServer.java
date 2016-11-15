package com.rent.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan("com.rent.auth," +
               "com.rent.utility," +
               "com.rent.data.dataaccess.auth," +
               "com.rent.data.configuration")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class OAuthServer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(OAuthServer.class, args);

        System.out.println("Starting Oauth Server\nBeans:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}