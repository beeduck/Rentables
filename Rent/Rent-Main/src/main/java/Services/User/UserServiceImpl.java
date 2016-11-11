package Services.User;

import Utilities.DateUtils;
import dataaccess.api.dao.Registration.VerificationTokenDAO;
import dataaccess.api.dao.User.UserDAO;
import dataaccess.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Duck on 10/28/2016.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    VerificationTokenDAO verificationTokenDAO;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User updateUser(User user) {
        userDAO.updateUser(user);

        return user;
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public User getUserByName(String username) {
        return userDAO.getUserByUsername(username);
    }

    public void updateUserModifiedTime(User user) {
        user.setLastEditDate(DateUtils.getCurrentUtcTimestamp());

        userDAO.updateUser(user);
    }

    public void updateUserModifiedTime(String username) {
        User user = userDAO.getUserByUsername(username);

        updateUserModifiedTime(user);
    }
}
