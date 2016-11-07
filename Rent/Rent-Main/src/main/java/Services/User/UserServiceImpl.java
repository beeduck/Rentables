package Services.User;

import DTOEntities.User.UserDTO;
import Events.Registration.RegistrationCompleteEvent;
import Utilities.Constants;
import Utilities.DateUtils;
import dataAccess.dao.Registration.VerificationTokenDAO;
import dataAccess.dao.User.UserDAO;
import dataAccess.entities.Registration.VerificationToken;
import dataAccess.entities.user.User;
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

        Timestamp timestamp = DateUtils.getCurrentUtcTimestamp();
        user.setCreateDate(timestamp);
        user.setLastEditDate(timestamp);

        userDAO.createUser(user);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user, locale));

        return user;
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public User getUserByName(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Transactional
    public boolean createVerificationToken(User user, String token) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getCurrentUtcTimestamp());
        cal.add(Calendar.MINUTE, Constants.TOKEN_TIME_DURATION);
        Date expirationDate = new Date(cal.getTime().getTime());

        VerificationToken verificationToken = new VerificationToken(token, user.getId(), expirationDate);

        verificationTokenDAO.saveVerificationToken(verificationToken);

        return true;
    }

    @Transactional
    public void completeRegistration(String token) throws Exception {
        VerificationToken verificationToken = verificationTokenDAO.getTokenByString(token);

        if(verificationToken == null) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Invalid token: " + token);
        }

        User user = userDAO.getUserById(verificationToken.getUserId());

        if( !verificationToken.getExpirationDate().after(new Date()) ) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Expired token: " + token);
        }

        user.setActive(true);
        userDAO.toggleUserActivation(user);
        verificationTokenDAO.removeToken(verificationToken);
    }

    public void updateUserModifiedTime(User user) {
        user.setLastEditDate(DateUtils.getCurrentUtcTimestamp());

        userDAO.updateUser(user);
    }

    public void updateUserModifiedTime(String username) {
        User user = userDAO.getUserByUsername(username);

        updateUserModifiedTime(user);
    }
}
