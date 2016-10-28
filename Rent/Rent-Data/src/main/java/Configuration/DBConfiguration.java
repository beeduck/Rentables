package Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
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
import java.beans.PropertyVetoException;
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
        localSessionFactoryBuilder.scanPackages("dataAccess.entities")
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
    public DataSource dataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(dbPropertiesPlaceholder.getDriver());
        } catch (PropertyVetoException e) {
            System.out.println(e);  // TODO: Log error
        }
        comboPooledDataSource.setJdbcUrl(dbPropertiesPlaceholder.getUrl());
        comboPooledDataSource.setUser(dbPropertiesPlaceholder.getUsername());
        comboPooledDataSource.setPassword(dbPropertiesPlaceholder.getPassword());

        comboPooledDataSource.setInitialPoolSize(3);
        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setMaxPoolSize(100);
        comboPooledDataSource.setIdleConnectionTestPeriod(150);
        comboPooledDataSource.setAcquireIncrement(1);
        comboPooledDataSource.setMaxStatements(0);
        comboPooledDataSource.setNumHelperThreads(30);
        return comboPooledDataSource;
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
