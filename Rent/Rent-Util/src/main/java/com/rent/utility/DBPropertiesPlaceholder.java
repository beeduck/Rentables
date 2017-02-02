package com.rent.utility;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

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

    public static ComboPooledDataSource setComboPooledDataSource(String driverClass, String jdbcUrl,
                                                                 String username, String password, Logger logger) {

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(driverClass);
        } catch (PropertyVetoException e) {
            logger.error(e.getMessage());
        }
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(username);
        comboPooledDataSource.setPassword(password);

        setDBPoolSettings(comboPooledDataSource);

        return comboPooledDataSource;
    }

    public static void setDBPoolSettings(ComboPooledDataSource comboPooledDataSource) {
        comboPooledDataSource.setInitialPoolSize(3);
        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setMaxPoolSize(100);
        comboPooledDataSource.setIdleConnectionTestPeriod(150);
        comboPooledDataSource.setAcquireIncrement(1);
        comboPooledDataSource.setMaxStatements(0);
        comboPooledDataSource.setNumHelperThreads(30);
    }

    public static LocalContainerEntityManagerFactoryBean setEntityManagerFactory(DataSource dataSource,
                                                                                 String packages,
                                                                                 String databasePlatform) {

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan(packages);
        factoryBean.setDataSource(dataSource);

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform(databasePlatform);

        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        return factoryBean;
    }

}
