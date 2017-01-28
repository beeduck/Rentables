package com.rent.data.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rent.utility.DBPropertiesPlaceholder;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by duck on 10/24/16.
 */
@Configuration
@EnableTransactionManagement
public class DBConfiguration {
    @Autowired
    private DBPropertiesPlaceholder dbPropertiesPlaceholder;

    private final Logger logger = LoggerFactory.getLogger(DBConfiguration.class);

    @Bean(name = "apiSessionFactory")
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(apiDataSource());
        localSessionFactoryBuilder.scanPackages("com.rent.data.dataaccess.api.entities")
            .addProperties(getHibernateProperties());
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    @Bean(name = "authSessionFactory")
    public SessionFactory authSessionFactory() {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(authDataSource());
        localSessionFactoryBuilder.scanPackages("com.rent.data.dataaccess.auth.entities")
                .addProperties(getHibernateProperties());
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        // TODO: Move values to properties file
        Properties properties = new Properties();
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.jdbc.batch_size", 50);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean(destroyMethod = "close")
    public DataSource apiDataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(dbPropertiesPlaceholder.getApiDriver());
        } catch (PropertyVetoException e) {
            logger.error(e.getMessage());
        }
        comboPooledDataSource.setJdbcUrl(dbPropertiesPlaceholder.getApiUrl());
        comboPooledDataSource.setUser(dbPropertiesPlaceholder.getApiUsername());
        comboPooledDataSource.setPassword(dbPropertiesPlaceholder.getApiPassword());

        setDBPoolSettings(comboPooledDataSource);

        return comboPooledDataSource;
    }

    @Bean(destroyMethod = "close")
    public DataSource authDataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(dbPropertiesPlaceholder.getAuthDriver());
        } catch (PropertyVetoException e) {
            System.out.println(e);  // TODO: Log error
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

    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
