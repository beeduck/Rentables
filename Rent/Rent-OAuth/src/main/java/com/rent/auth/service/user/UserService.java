package com.rent.auth.service.user;

import com.rent.auth.dto.user.UserDTO;
import com.rent.auth.entities.user.UserDetails;

import java.util.Locale;

/**
 * Created by Duck on 11/11/2016.
 */
public interface UserService {

    UserDetails createUser(UserDTO userDTO, Locale locale);

    UserDetails updateUser(UserDTO userDTO);

    void completeRegistration(String token) throws Exception;

    void confirmEmailChange(String token) throws Exception;
}
