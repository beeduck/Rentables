package dataAccess.dao.User;

import dataAccess.entities.User;

import java.util.List;

/**
 * Created by duck on 10/25/16.
 */
public interface UserDAO {

    @SuppressWarnings("unchecked")
    List<User> getAllUsers();

    User getUserByUsername(String username);

    boolean updateUser(User user);

    User createUser(User user);

    User getUserById(int userId);

    boolean toggleUserActivation(User user);

}