package Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Duck on 10/28/2016.
 */
@Configuration
@ComponentScan("Configuration")
public class MailConfig {

    @Autowired
    private GeneralProperties generalProperties;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(generalProperties.getEmailHost());
        javaMailSender.setProtocol(generalProperties.getEmailProtocol());
        javaMailSender.setPort(generalProperties.getEmailPort());
        javaMailSender.setUsername(generalProperties.getEmailUsername());

        return javaMailSender;
    }
}
