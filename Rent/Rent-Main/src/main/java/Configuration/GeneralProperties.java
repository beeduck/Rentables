package Configuration;

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

    @Value("${email.host}")
    private String emailHost;

    @Value("${email.protocol}")
    private String emailProtocol;

    @Value("${email.port}")
    private int emailPort;

    @Value("${email.username}")
    private String emailUsername;

    public String getEmailUsername() {
        return emailUsername;
    }

    public int getEmailPort() {

        return emailPort;
    }

    public String getEmailProtocol() {

        return emailProtocol;
    }

    public String getEmailHost() {

        return emailHost;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
