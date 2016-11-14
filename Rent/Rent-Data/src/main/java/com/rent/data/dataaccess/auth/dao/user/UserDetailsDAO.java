package com.rent.data.dataaccess.auth.dao.user;

import com.rent.data.dataaccess.auth.entities.user.UserDetails;

import java.util.List;

/**
 * Created by duck on 10/25/16.
 */
public interface UserDetailsDAO {

    @SuppressWarnings("unchecked")
    List<UserDetails> getAllUsers();

    UserDetails getUserByUsername(String username);

    boolean updateUser(UserDetails user);

    UserDetails createUser(UserDetails user);

    UserDetails getUserById(int userId);

    boolean toggleUserActivation(UserDetails user);

}