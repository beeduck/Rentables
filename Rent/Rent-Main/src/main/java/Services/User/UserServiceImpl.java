package Services.User;

import DTOEntities.User.UserDTO;
import Events.Registration.RegistrationCompleteEvent;
import dataAccess.dao.Registration.VerificationTokenDAO;
import dataAccess.dao.User.UserDAO;
import dataAccess.entities.Registration.VerificationToken;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Duck on 10/28/2016.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    VerificationTokenDAO verificationTokenDAO;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User updateUser(User user) {
        userDAO.updateUser(user);

        return user;
    }

    @Transactional
    public User createUser(UserDTO userDTO, Locale locale) {


        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setCreateDate(timestamp);
        user.setLastEditDate(timestamp);

        userDAO.createUser(user);

//        String appUrl = "beeduck.ddns.net:8080";  // TODO: Change to retrieving request context path from controller

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user, locale));

        return user;
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Transactional
    public boolean createVerificationToken(User user, String token) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, 60*24);
        Date expirationDate = new Date(cal.getTime().getTime());

        VerificationToken verificationToken = new VerificationToken(token, user.getId(), expirationDate);

        verificationTokenDAO.saveVerificationToken(verificationToken);

        return true;
    }

    @Transactional
    public void completeRegistration(String token) {
        VerificationToken verificationToken = verificationTokenDAO.getTokenByString(token);

        if(verificationToken == null) {
            // TODO: Send error if token does not exist
            return;
        }

        User user = userDAO.getUserById(verificationToken.getUserId());

        if( !verificationToken.getExpirationDate().after(new Date()) ) {
            // TODO: Send error if token has expired
            return;
        }

        user.setActive(true);
        userDAO.toggleUserActivation(user);
        verificationTokenDAO.removeToken(verificationToken);
    }
}
