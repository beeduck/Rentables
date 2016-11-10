package Controllers;

import dataaccess.api.dao.User.UserDAO;
import dataaccess.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@ComponentScan("dataaccess")
public class GeneralController {

    @Autowired
    UserDAO userDao;

    @RequestMapping("/")
    public List<User> index() {
        List<User> userList = userDao.getAllUsers();
        return userList;
    }

}