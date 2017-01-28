package com.rent.auth.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rent.utility.DBPropertiesPlaceholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by Duck on 1/27/2017.
 */
@Configuration
public class DBConfiguration {
    private final Logger logger = LoggerFactory.getLogger(DBConfiguration.class);

    @Autowired
    private DBPropertiesPlaceholder dbPropertiesPlaceholder;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(dbPropertiesPlaceholder.getAuthDriver());
        } catch (PropertyVetoException e) {
            logger.error(e.getMessage());
        }
        comboPooledDataSource.setJdbcUrl(dbPropertiesPlaceholder.getAuthUrl());
        comboPooledDataSource.setUser(dbPropertiesPlaceholder.getAuthUsername());
        comboPooledDataSource.setPassword(dbPropertiesPlaceholder.getAuthPassword());

        setDBPoolSettings(comboPooledDataSource);

        return comboPooledDataSource;
    }

    private void setDBPoolSettings(ComboPooledDataSource comboPooledDataSource) {
        comboPooledDataSource.setInitialPoolSize(3);
        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setMaxPoolSize(100);
        comboPooledDataSource.setIdleConnectionTestPeriod(150);
        comboPooledDataSource.setAcquireIncrement(1);
        comboPooledDataSource.setMaxStatements(0);
        comboPooledDataSource.setNumHelperThreads(30);
    }
}
