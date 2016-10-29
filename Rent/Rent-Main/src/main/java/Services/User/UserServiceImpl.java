package Services.User;

import DTOEntities.User.UserDTO;
import dataAccess.dao.User.UserDAO;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

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

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setCreateDate(timestamp);
        user.setLastEditDate(timestamp);

        userDAO.createUser(user);

        return user;
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}
