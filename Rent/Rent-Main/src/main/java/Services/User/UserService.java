package Services.User;

import dataaccess.api.entities.User;

/**
 * Created by Duck on 10/28/2016.
 */
public interface UserService {
    User updateUser(User user);

    User getUserById(int userId);

    User getUserByName(String username);

    void updateUserModifiedTime(User user);

    void updateUserModifiedTime(String username);
}
