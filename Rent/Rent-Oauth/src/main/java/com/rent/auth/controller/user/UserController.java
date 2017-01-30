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
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/createUser", method = RequestMethod.POST,
            headers = Constants.CONTENT_TYPE_JSON)
    public UserDetails createUser(@Valid @RequestBody final UserDTO userDTO,
                                  HttpServletRequest request) {

        return userService.createUser(userDTO, request.getLocale());
    }

    @RequestMapping(value = "/completeRegistration", method = RequestMethod.GET)
    public UserDetails registerUser(@RequestParam("token") final String token) throws Exception {

        userService.completeRegistration(token);

        // TODO: Return confirmation
        return null;
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public UserDetails updateUserPassword() {
        // TODO: Implement password update
        return null;
    }
}
