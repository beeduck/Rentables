package com.rent.api.dataaccess.api.dao.user;

import com.rent.api.dataaccess.api.entities.user.UserInfo;

/**
 * Created by Duck on 11/11/2016.
 */
public interface UserInfoDAO {

    boolean addUser(int userId, String username);

    boolean updateUser(UserInfo user);

    UserInfo getUserByUsername(String username);

    UserInfo getUserById(int userId);
}
