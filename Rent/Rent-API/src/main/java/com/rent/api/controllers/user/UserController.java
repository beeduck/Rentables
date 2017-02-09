package com.rent.api.controllers.user;

import com.rent.api.entities.user.UserInfo;
import com.rent.api.services.user.UserInfoService;
import com.rent.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    UserInfoService userInfoService;

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(method = RequestMethod.GET)
    public UserInfo getCurrentUserInfo() {
        return userInfoService.getCurrentUserInfo();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserInfo getUserInfo(@PathVariable("userId") int userId) {

        return null;
    }
}
