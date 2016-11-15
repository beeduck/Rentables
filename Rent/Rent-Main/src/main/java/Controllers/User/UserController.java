package Controllers.User;

import com.rent.data.dataaccess.api.entities.user.UserInfo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Duck on 10/28/2016.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.GET)
    public UserInfo getUserInfo(@PathVariable("userId") int userId) {
//        user user = userService.getUserById(userId);

//        return user;
        return null;
    }
}
