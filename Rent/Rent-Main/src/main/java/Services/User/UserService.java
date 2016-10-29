package Services.User;

import DTOEntities.User.UserDTO;
import dataAccess.entities.User;

/**
 * Created by Duck on 10/28/2016.
 */
public interface UserService {
    User updateUser(User user);

    User createUser(UserDTO userDTO);

    User getUserById(int userId);
}
