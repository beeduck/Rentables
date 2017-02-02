package com.rent.auth.configuration;

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

    @Primary
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

        DBPropertiesPlaceholder.setDBPoolSettings(comboPooledDataSource);

        return comboPooledDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan("com.rent.auth.entities");
        factoryBean.setDataSource(dataSource());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        LocalContainerEntityManagerFactoryBean bean = entityManagerFactory();
        return new JpaTransactionManager(bean.getObject());
    }
}
