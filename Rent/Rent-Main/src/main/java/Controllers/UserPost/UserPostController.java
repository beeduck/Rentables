package Controllers.UserPost;

import DTOEntities.UserPost.UserPostDTO;
import Services.UserPost.UserPostService;
import Utilities.Filters.UserPostFilter;
import dataaccess.api.entities.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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

    @RequestMapping(value = "/getPostsById/{id}", method = RequestMethod.GET)
    public UserPost getPostsById(@PathVariable("id") int id) {
        return userPostService.getPostById(id);
    }

    @RequestMapping(value = "/getPosts", method = RequestMethod.GET)
    public List<UserPost> getPosts(UserPostFilter filter) {
        return userPostService.getPosts(filter);
    }

    @RequestMapping(value = "/createPost", method = RequestMethod.POST,
            headers = "content-type=application/json")
    public void createPost(@Valid @RequestBody final UserPostDTO userPostDTO) {
        userPostService.createPost(userPostDTO);
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST,
            headers = "content-type=application/json")
    public void updatePost(@Valid @RequestBody final UserPost post) {
        userPostService.updateUser(post);
    }
}
