package Configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by duck on 10/24/16.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan
public class DBConfiguration {
    @Autowired
    private DBPropertiesPlaceholder dbPropertiesPlaceholder;

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
        localSessionFactoryBuilder.scanPackages("dataaccess.entities")
            .addProperties(getHibernateProperties());
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dbPropertiesPlaceholder.getDriver());
        driverManagerDataSource.setUrl(dbPropertiesPlaceholder.getUrl());
        driverManagerDataSource.setUsername(dbPropertiesPlaceholder.getUsername());
        driverManagerDataSource.setPassword(dbPropertiesPlaceholder.getPassword());
        return driverManagerDataSource;
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
