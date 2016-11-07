package Events.Registration;

import dataAccess.entities.user.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by Duck on 10/28/2016.
 */
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private Locale locale;

    public RegistrationCompleteEvent(User user, Locale locale) {
        super(user);

        this.user = user;
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
