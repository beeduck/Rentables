package Controllers.UserPost;

import DTOEntities.UserPost.UserPostDTO;
import Services.UserPost.UserPostService;
import dataAccess.entities.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Asad on 11/1/2016.
 */

@RestController
@RequestMapping("/userPosts")
@ComponentScan("Controllers")
public class UserPostController {

    @Autowired
    UserPostService userPostService;

    @RequestMapping(value = "/getPostsByUserId/{userId}", method = RequestMethod.GET)
    public List<UserPost> getPostsByUserId(@PathVariable("userId") int userId) {
        return userPostService.getPostsByUserId(userId);
    }

    @RequestMapping(value = "/getPostsById/{id}", method = RequestMethod.GET)
    public UserPost getPostsById(@PathVariable("id") int id) {
        return userPostService.getPostById(id);
    }

    @RequestMapping(value = "/createPost", method = RequestMethod.POST,
            headers = "content-type=application/json")
    public void createPost(@Valid @RequestBody final UserPostDTO userPostDTO,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            // TODO: Handle binding result errors
            System.out.println("Gotta do something here....");
        }
        userPostService.createPost(userPostDTO);
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST,
            headers = "content-type=application/json")
    public void updatePost(@Valid @RequestBody final UserPost post,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("Gotta do something here too....");
        }
        userPostService.updateUser(post);
    }
}
