package Controllers.User;

import Services.User.UserService;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Duck on 10/28/2016.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user")
    public User getUserInfo() {
        User user = new User();
        user.setUsername("return test");
        return user;
    }
}
