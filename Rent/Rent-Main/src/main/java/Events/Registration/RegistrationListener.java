package Events.Registration;

import Configuration.GeneralProperties;
import Services.User.UserService;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Duck on 10/28/2016.
 */
@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private GeneralProperties generalProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(RegistrationCompleteEvent event) {
        User user = event.getUser();

        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getUsername();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = "/users/registrationConfirm?token=" + token;

        // TODO: Create email template
        //String message = messages.getMessage("mailconfirm.mail.body", null, event.getLocale());
        String message = "Test";


        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(generalProperties.getEmailUsername());
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " rn" + "localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
