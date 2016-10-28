package Services.User;

import dataAccess.dao.User.UserDAO;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Duck on 10/28/2016.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    public User updateUser(User user) {
        userDAO.updateUser(user);

        return user;
    }

    public User createUser(User user) {
        userDAO.createUser(user);

        return user;
    }
}
