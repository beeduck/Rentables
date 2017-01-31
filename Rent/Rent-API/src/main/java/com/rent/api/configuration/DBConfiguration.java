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

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by Duck on 1/27/2017.
 */
@Configuration
public class DBConfiguration {
    private final Logger logger = LoggerFactory.getLogger(DBConfiguration.class);

    @Autowired
    private DBPropertiesPlaceholder dbProperties;

    @Primary
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        return DBPropertiesPlaceholder.setComboPooledDataSource(
                dbProperties.getApiDriver(), dbProperties.getApiUrl(),
                dbProperties.getApiUsername(), dbProperties.getApiPassword(), logger
        );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return DBPropertiesPlaceholder.setEntityManagerFactory(dataSource(), "com.rent.api.entities",
                                                               "org.hibernate.dialect.MySQL5Dialect");
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        LocalContainerEntityManagerFactoryBean bean = entityManagerFactory();
        return new JpaTransactionManager(bean.getObject());
    }
}
