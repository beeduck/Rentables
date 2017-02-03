package com.rent.api.services.user;

import com.rent.api.dao.user.UserInfoRepository;
import com.rent.api.entities.user.UserInfo;
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
    UserInfoRepository userInfoRepository;

    @Transactional
    public void addNewUserInfo(NewUserDTO newUserDTO) {
        UserInfo userInfo = new UserInfo(newUserDTO.getUserId(), newUserDTO.getUsername());

        userInfoRepository.save(userInfo);
    }

    @Transactional
    public void updateUserEmail(NewUserDTO newUserDTO) {
        UserInfo userInfo = userInfoRepository.findById(newUserDTO.getUserId());

        userInfo.setUsername(newUserDTO.getUsername());

        userInfoRepository.save(userInfo);
    }

}
