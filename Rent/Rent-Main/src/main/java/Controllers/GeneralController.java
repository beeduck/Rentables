package Controllers;

import dataAccess.dao.User.UserDAO;
import dataAccess.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@ComponentScan("dataAccess")
public class GeneralController {

    @Autowired
    UserDAO userDao;

    @RequestMapping("/")
    public List<User> index() {
        List<User> userList = userDao.getAllUsers();
        return userList;
    }

}