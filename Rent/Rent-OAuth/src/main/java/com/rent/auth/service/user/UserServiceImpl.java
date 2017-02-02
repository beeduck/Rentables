package com.rent.auth.service.user;

import com.rent.auth.configuration.GeneralProperties;
import com.rent.auth.dao.registration.RegistrationRepository;
import com.rent.auth.dao.user.UserDetailsRepository;
import com.rent.auth.dto.user.UserDTO;
import com.rent.auth.entities.registration.RegistrationToken;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private GeneralProperties generalProperties;

    // TODO: Add rollback for failed email send and fail from feign request
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

        registrationRepository.save(registrationToken);

        return true;
    }

    @Transactional
    public void completeRegistration(String token) throws Exception {
        RegistrationToken registrationToken = registrationRepository.findByToken(token);

        if(registrationToken == null) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Invalid registration token: " + token);
        }

        UserDetails user = userDetailsRepository.findById(registrationToken.getUserId());

        if( !registrationToken.getExpirationDate().after(new Date()) ) {
            // TODO: Create custom exception class
            throw new IllegalArgumentException("Expired registration token: " + token);
        }

        user.setActive(true);
        userDetailsRepository.save(user);
        registrationRepository.delete(registrationToken);
    }
}
