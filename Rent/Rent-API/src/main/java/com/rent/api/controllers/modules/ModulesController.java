package com.rent.api.controllers.modules;

import com.rent.api.services.user.UserInfoService;
import com.rent.utility.Constants;
import com.rent.utility.dto.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Duck on 11/20/2016.
 */

@RestController
@RequestMapping("/module")
@PreAuthorize("hasAuthority('ROLE_MODULE')")
public class ModulesController {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping(value = Constants.PATH_COMPLETE_USER_REGISTRATION, method = RequestMethod.POST,
            headers = Constants.CONTENT_TYPE_JSON)
    public void newUserCreation(@RequestBody final NewUserDTO newUserDTO) {
        userInfoService.addNewUserInfo(newUserDTO);
    }
}
