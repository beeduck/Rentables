package com.rent.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@ComponentScan("com.rent.auth," +
               "com.rent.utility")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableDiscoveryClient
@EnableFeignClients
public class OAuthServer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(OAuthServer.class, args);
    }

}