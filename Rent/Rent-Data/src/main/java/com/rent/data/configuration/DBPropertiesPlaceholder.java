package com.rent.data.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by duck on 10/24/16.
 */
@Configuration
@PropertySource({"classpath:jdbc.properties"})
public class DBPropertiesPlaceholder {
    @Value("${jdbc.apiDriver}")
    private String apiDriver;

    @Value("${jdbc.apiUrl}")
    private String apiUrl;

    @Value("${jdbc.apiUsername}")
    private String apiUsername;

    @Value("${jdbc.apiPassword}")
    private String apiPassword;

    @Value("${jdbc.authDriver}")
    private String authDriver;

    @Value("${jdbc.authUrl}")
    private String authUrl;

    @Value("${jdbc.authUsername}")
    private String authUsername;

    @Value("${jdbc.authPassword}")
    private String authPassword;

    public String getApiDriver() {
        return apiDriver;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiUsername() {
        return apiUsername;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public String getAuthUsername() {

        return authUsername;
    }

    public String getAuthUrl() {

        return authUrl;
    }

    public String getAuthDriver() {

        return authDriver;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
