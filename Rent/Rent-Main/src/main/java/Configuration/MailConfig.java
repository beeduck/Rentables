package Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Duck on 10/28/2016.
 */
@Configuration
public class MailConfig {
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("aspmx.l.google.com");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setPort(25);
        javaMailSender.setUsername("b33duck@gmail.com");

        return javaMailSender;
    }
}
