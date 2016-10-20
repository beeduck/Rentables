package Wang;

import dataaccess.dao.UserDAO;
import dataaccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ComponentScan("dataaccess")
public class HelloController {

    @Autowired
    UserDAO userDao;

    @RequestMapping("/")
    public String index() {
        List<User> userList = userDao.getAllUsers();
        return "Greetings from Spring Boot!";
    }

}