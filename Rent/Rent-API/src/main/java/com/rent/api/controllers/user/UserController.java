package com.rent.api.controllers.user;

import com.rent.api.entities.user.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Duck on 10/28/2016.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserInfo getUserInfo(@PathVariable("userId") int userId) {

        return null;
    }
}
