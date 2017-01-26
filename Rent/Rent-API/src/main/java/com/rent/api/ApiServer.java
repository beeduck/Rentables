package com.rent.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.Arrays;

@Configuration
@ComponentScan("com.rent.api," +
               "com.rent.utility," +
               "com.rent.data.configuration," +
               "com.rent.data.dataaccess.api")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
public class ApiServer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApiServer.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}