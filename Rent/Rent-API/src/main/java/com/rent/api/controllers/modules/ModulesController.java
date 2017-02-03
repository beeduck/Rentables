package com.rent.api.controllers.modules;

import com.rent.api.services.user.UserInfoService;
import com.rent.utility.Constants;
import com.rent.utility.dto.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Duck on 11/20/2016.
 */

@RestController
@RequestMapping(Constants.SHARED_BASE_PATH)
@PreAuthorize(Constants.AUTH_ROLE_MODULE)
public class ModulesController {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping(value = Constants.SHARED_COMPLETE_USER_REGISTRATION_PATH, method = RequestMethod.POST,
            headers = Constants.CONTENT_TYPE_JSON)
    public void newUserCreation(@RequestBody final NewUserDTO newUserDTO) {
        userInfoService.addNewUserInfo(newUserDTO);
    }

    @RequestMapping(value = Constants.SHARED_CHANGE_USER_EMAIL_PATH, method = RequestMethod.PUT,
            headers = Constants.CONTENT_TYPE_JSON)
    public void updateUserEmail(@RequestParam("user-id") final int userId,
                                @RequestParam("new-email") final String newEmail) {
        userInfoService.updateUserEmail(userId, newEmail);
    }
}
