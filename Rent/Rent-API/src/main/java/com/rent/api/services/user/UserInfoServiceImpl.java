package com.rent.api.services.user;

import com.rent.data.dataaccess.api.dao.user.UserInfoDAO;
import com.rent.utility.dto.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Duck on 11/20/2016.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDAO userInfoDAO;

    @Transactional
    public void addNewUserInfo(NewUserDTO newUserDTO) {
        userInfoDAO.addUser(newUserDTO.getUserId(), newUserDTO.getUsername());
    }

}
