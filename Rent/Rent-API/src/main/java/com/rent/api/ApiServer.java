package com.rent.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@ComponentScan("com.rent.api," +
               "com.rent.utility")
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
public class ApiServer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApiServer.class, args);
    }

}