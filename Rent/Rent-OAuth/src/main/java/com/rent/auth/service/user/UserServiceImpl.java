package com.rent.auth.service.user;

import com.rent.auth.configuration.GeneralProperties;
import com.rent.auth.dao.registration.RegistrationRepository;
import com.rent.auth.dao.user.EmailChangeTokenRepository;
import com.rent.auth.dao.user.UserDetailsRepository;
import com.rent.auth.dto.user.UserDTO;
import com.rent.auth.entities.registration.RegistrationToken;
import com.rent.auth.entities.user.EmailChangeToken;
import com.rent.auth.entities.user.UserDetails;
import com.rent.auth.proxy.RentAPIProxy;
import com.rent.utility.Constants;
import com.rent.utility.DateUtils;
import com.rent.utility.dto.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private RentAPIProxy rentAPIProxy;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EmailChangeTokenRepository emailChangeTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        userDetailsRepository.save(user);

        NewUserDTO newUserDTO = new NewUserDTO(user.getUsername(), user.getId());
        rentAPIProxy.registerUser(newUserDTO);

        String token = createRegistrationToken(user);
        registrationEmail(user, locale, token);

        return user;
    }

    @Transactional
    public UserDetails updateUser(UserDTO userDTO) {
        UserDetails userDetails = null;//userDetailsRepository.findById();

        boolean changed = false;
        if(!userDetails.getUsername().equalsIgnoreCase(userDTO.getUsername()) && (userDTO.getUsername() != null) ) {
            // Send email to verify change of email address
            String token = createEmailChangeToken(userDetails, userDTO.getUsername());
            usernameChangeEmail(userDetails, token);
        }

        if(!passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword())) {
            userDetails.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            changed = true;
        }

        if(changed) {
            return userDetailsRepository.save(userDetails);
        }

        return userDetails;
    }

    private void registrationEmail(UserDetails user, Locale locale, String token) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(generalProperties.getEmailUsername());
        simpleMailMessage.setTo(user.getUsername());
        simpleMailMessage.setSubject("Registration Confirmation");
        simpleMailMessage.setText("Confirm registration: localhost:8081" +
                        Constants.USER_BASE_PATH + Constants.USER_CONFIRM_REGISTRATION_PATH + "?token=" + token);
        mailSender.send(simpleMailMessage);
    }

    private void usernameChangeEmail(UserDetails user, String token) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(generalProperties.getEmailUsername());
        simpleMailMessage.setTo(user.getUsername());
        simpleMailMessage.setSubject("Change of Email Confirmation");
        simpleMailMessage.setText("Confirm change of email address: localhost:8081" +
                                  Constants.USER_BASE_PATH + Constants.USER_CONFIRM_EMAIL_PATH + "?token=" + token);
        mailSender.send(simpleMailMessage);
    }

    private String createRegistrationToken(UserDetails user) {
        String token = UUID.randomUUID().toString();

        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getCurrentUtcTimestamp());
        cal.add(Calendar.MINUTE, Constants.REGISTRATION_TOKEN_TIME_DURATION);
        Date expirationDate = new Date(cal.getTime().getTime());

        RegistrationToken registrationToken = new RegistrationToken(token, user.getId(), expirationDate);

        registrationRepository.save(registrationToken);

        return token;
    }

    private String createEmailChangeToken(UserDetails user, String newEmail) {
        String token = UUID.randomUUID().toString();

        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getCurrentUtcTimestamp());
        cal.add(Calendar.MINUTE, Constants.REGISTRATION_TOKEN_TIME_DURATION);
        Date expirationDate = new Date(cal.getTime().getTime());

        EmailChangeToken emailChangeToken = new EmailChangeToken(token, user.getId(), expirationDate, newEmail);

        emailChangeTokenRepository.save(emailChangeToken);

        return token;
    }

    @Transactional
    public void completeRegistration(String token) throws Exception {
        RegistrationToken registrationToken = registrationRepository.findByToken(token);

        if(registrationToken == null) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Invalid registration token: " + token);
        }

        if( !registrationToken.getExpirationDate().after(new Date()) ) {
            // TODO: Create custom exception class
            // TODO: Send new registration email
            throw new IllegalArgumentException("Expired registration token: " + token);
        }

        UserDetails user = userDetailsRepository.findById(registrationToken.getUserId());

        user.setActive(true);
        userDetailsRepository.save(user);
        registrationRepository.delete(registrationToken);
    }

    @Transactional
    public void confirmEmailChange(String token) throws Exception {
        EmailChangeToken emailChangeToken = emailChangeTokenRepository.findByToken(token);

        if(emailChangeToken == null) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Invalid email change token: " + token);
        }

        if( !emailChangeToken.getExpirationDate().after(new Date()) ) {
            // TODO: Create custom exception class
            // TODO: Send new registration email
            throw new IllegalArgumentException("Expired email change token: " + token);
        }

        UserDetails userDetails = userDetailsRepository.findById(emailChangeToken.getUserId());

        userDetails.setUsername(emailChangeToken.getNewEmail());
        NewUserDTO newUserDTO = new NewUserDTO(userDetails.getUsername(), userDetails.getId());
        rentAPIProxy.updateUserEmail(newUserDTO);

        userDetailsRepository.save(userDetails);
        emailChangeTokenRepository.delete(emailChangeToken);
    }

}
