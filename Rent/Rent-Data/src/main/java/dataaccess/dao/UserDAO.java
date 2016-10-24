package dataaccess.dao;

import dataaccess.entities.User;

import java.util.List;

/**
 * Created by duck on 10/25/16.
 */
public interface UserDAO {

    @SuppressWarnings("unchecked")
    List<User> getAllUsers();

}