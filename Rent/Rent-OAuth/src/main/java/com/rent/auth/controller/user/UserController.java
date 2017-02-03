package com.rent.auth.controller.user;

import com.rent.auth.dto.user.UserDTO;
import com.rent.auth.service.user.UserService;
import com.rent.auth.entities.user.UserDetails;
import com.rent.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Duck on 11/11/2016.
 */
@RestController
@RequestMapping(Constants.USER_BASE_PATH)
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST,
            headers = Constants.CONTENT_TYPE_JSON)
    public UserDetails createUser(@Valid @RequestBody final UserDTO userDTO,
                                  HttpServletRequest request) {

        return userService.createUser(userDTO, request.getLocale());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public UserDetails updateUser(@Valid @RequestBody final UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @RequestMapping(value = Constants.USER_CONFIRM_REGISTRATION_PATH, method = RequestMethod.GET)
    public UserDetails confirmUserRegistration(@RequestParam("token") final String token) throws Exception {

        userService.completeRegistration(token);

        // TODO: Return confirmation
        return null;
    }

    @RequestMapping(value = Constants.USER_CONFIRM_EMAIL_PATH, method = RequestMethod.GET)
    public UserDetails confirmEmailChange(@RequestParam("token") final String token) throws Exception {
        userService.confirmEmailChange(token);

        // TODO: Return confirmation
        return null;
    }
}
