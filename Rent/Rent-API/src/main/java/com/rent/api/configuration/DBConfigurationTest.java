package com.rent.api.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rent.utility.DBPropertiesPlaceholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by Duck on 1/27/2017.
 */
@Configuration
public class DBConfigurationTest {
    private final Logger logger = LoggerFactory.getLogger(DBConfigurationTest.class);

    @Autowired
    private DBPropertiesPlaceholder dbPropertiesPlaceholder;

    @Primary
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
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

    private void setDBPoolSettings(ComboPooledDataSource comboPooledDataSource) {
        comboPooledDataSource.setInitialPoolSize(3);
        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setMaxPoolSize(100);
        comboPooledDataSource.setIdleConnectionTestPeriod(150);
        comboPooledDataSource.setAcquireIncrement(1);
        comboPooledDataSource.setMaxStatements(0);
        comboPooledDataSource.setNumHelperThreads(30);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan("com.rent.api.entities");
        factoryBean.setDataSource(dataSource());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        return factoryBean;//.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        LocalContainerEntityManagerFactoryBean bean = entityManagerFactory();
        return new JpaTransactionManager(bean.getObject());
    }
}
