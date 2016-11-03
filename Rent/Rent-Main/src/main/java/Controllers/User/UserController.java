package Controllers.User;

import DTOEntities.User.UserDTO;
import Services.User.UserService;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Duck on 10/28/2016.
 */
@RestController
@RequestMapping("/users")
@ComponentScan("Controllers")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);

        return user;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST,
           headers = "content-type=application/json")
    public User createUser(@Valid @RequestBody final UserDTO userDTO,
                           HttpServletRequest request) {

        return userService.createUser(userDTO, request.getLocale());
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public User registerUser(@RequestParam("token") final String token) throws Exception {

        userService.completeRegistration(token);

        return null;
    }
}
