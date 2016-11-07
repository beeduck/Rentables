package Services.User;

import DTOEntities.User.UserDTO;
import dataAccess.entities.user.User;

import java.util.Locale;

/**
 * Created by Duck on 10/28/2016.
 */
public interface UserService {
    User updateUser(User user);

    User createUser(UserDTO userDTO, Locale locale);

    User getUserById(int userId);

    User getUserByName(String username);

    boolean createVerificationToken(User user, String token);

    void completeRegistration(String token) throws Exception;

    void updateUserModifiedTime(User user);

    void updateUserModifiedTime(String username);
}
