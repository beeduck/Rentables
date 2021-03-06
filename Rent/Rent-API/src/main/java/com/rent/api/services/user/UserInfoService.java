package com.rent.api.services.user;

import com.rent.api.entities.user.UserInfo;
import com.rent.utility.dto.NewUserDTO;

/**
 * Created by Duck on 11/20/2016.
 */
public interface UserInfoService {

    void addNewUserInfo(NewUserDTO newUserDTO);

    void updateUserEmail(NewUserDTO newUserDTO);

    UserInfo getCurrentUserInfo();
}
