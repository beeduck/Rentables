package Controllers.User;

import DTOEntities.User.UserDTO;
import Services.User.UserService;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Duck on 10/28/2016.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);

        return user;
    }

    @RequestMapping(value = "createUser", method = RequestMethod.POST,
           headers = "content-type=application/json")
    public User createUser(@Valid @RequestBody final UserDTO userDTO,
                           BindingResult bindingResult,
                           HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return null;
        }

        return userService.createUser(userDTO);
    }
}
