package com.rent.api;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

@Configuration
@ComponentScan("com.rent.api," +
               "com.rent.utility")
@EnableAutoConfiguration//(exclude = {DataSourceAutoConfiguration.class/*, SecurityAutoConfiguration.class*/})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiServer {
//
//    @Bean
//    public SessionFactory sessionFactory(EntityManagerFactory factory) {
//        if(factory.unwrap(SessionFactory.class) == null){
//            throw new NullPointerException("factory is not a hibernate factory");
//        }
//        return factory.unwrap(SessionFactory.class);
//    }

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