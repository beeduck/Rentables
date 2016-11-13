package com.rent.auth.service.user;

import Utilities.Constants;
import Utilities.DateUtils;
import com.rent.auth.configuration.GeneralProperties;
import com.rent.auth.dto.user.UserDTO;
import com.rent.data.dataaccess.auth.dao.registration.RegistrationTokenDAO;
import com.rent.data.dataaccess.auth.dao.user.UserDetailsDAO;
import com.rent.data.dataaccess.auth.entities.registration.RegistrationToken;
import com.rent.data.dataaccess.auth.entities.user.UserDetails;
import dataaccess.api.dao.User.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Duck on 11/11/2016.
 */
@Service("userService")
@ComponentScan("com.rent.data.dataaccess.auth, dataaccess")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDetailsDAO userDetailsDAO;

    @Autowired
    UserInfoDAO userInfoDAO;

    @Autowired
    RegistrationTokenDAO registrationTokenDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private GeneralProperties generalProperties;

    @Transactional
    public UserDetails createUser(UserDTO userDTO, Locale locale) {

        UserDetails user = new UserDetails();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        Timestamp timestamp = DateUtils.getCurrentUtcTimestamp();
        user.setCreateDate(timestamp);
        user.setLastEditDate(timestamp);

        userDetailsDAO.createUser(user);
        userInfoDAO.addUser(user.getId(), user.getUsername());

        String token = UUID.randomUUID().toString();
        createRegistrationToken(user, token);
        registrationEmail(user, locale, token);


        return user;
    }

    private void registrationEmail(UserDetails user, Locale locale, String token) {

        String recipientAddress = user.getUsername();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = "/users/completeRegistration?token=" + token;

        // TODO: Create email template

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(generalProperties.getEmailUsername());
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Confirm registration: localhost:8081" + confirmationUrl);
        mailSender.send(email);
    }

    private boolean createRegistrationToken(UserDetails user, String token) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getCurrentUtcTimestamp());
        cal.add(Calendar.MINUTE, Constants.REGISTRATION_TOKEN_TIME_DURATION);
        Date expirationDate = new Date(cal.getTime().getTime());

        RegistrationToken registrationToken = new RegistrationToken(token, user.getId(), expirationDate);

        registrationTokenDAO.saveRegistrationToken(registrationToken);

        return true;
    }

    @Transactional
    public void completeRegistration(String token) throws Exception {
        RegistrationToken registrationToken = registrationTokenDAO.getTokenByString(token);

        if(registrationToken == null) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Invalid registration token: " + token);
        }

        UserDetails user = userDetailsDAO.getUserById(registrationToken.getUserId());

        if( !registrationToken.getExpirationDate().after(new Date()) ) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Expired registration token: " + token);
        }

        user.setActive(true);
        userDetailsDAO.toggleUserActivation(user);
        registrationTokenDAO.removeToken(registrationToken);
    }
}
