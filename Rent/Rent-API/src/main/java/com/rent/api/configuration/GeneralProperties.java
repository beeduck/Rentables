package com.rent.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by duck on 10/31/16.
 */
@Configuration
@PropertySource({"classpath:general.properties"})
public class GeneralProperties {

    @Value("${auth.server.endpoint}")
    private String authServerEndpoint;

    @Value("${auth.server.client}")
    private String authClient;

    @Value("${auth.server.secret}")
    private String authSecret;

    public String getAuthServerEndpoint() {
        return authServerEndpoint;
    }

    public String getAuthClient() {
        return authClient;
    }

    public String getAuthSecret() {
        return authSecret;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
