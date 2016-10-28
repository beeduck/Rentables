package Wang;

import dataAccess.dao.User.UserDAO;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ComponentScan("dataAccess")
public class HelloController {

    @Autowired
    UserDAO userDao;

    @RequestMapping("/")
    public List<User> index() {
        List<User> userList = userDao.getAllUsers();
        return userList;
    }

}