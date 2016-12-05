package com.rent.auth.service.user;

import com.rent.auth.dto.user.UserDTO;
import com.rent.data.dataaccess.auth.entities.user.UserDetails;
import org.springframework.mail.MailException;

import java.util.Locale;

/**
 * Created by Duck on 11/11/2016.
 */
public interface UserService {
    UserDetails createUser(UserDTO userDTO, Locale locale);

    void completeRegistration(String token) throws Exception;
    void registrationEmail(UserDetails user, Locale locale, String token) throws MailException;

    }
