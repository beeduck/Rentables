package Services.UserPost;

import DTOEntities.UserPost.UserPostDTO;
import Utilities.Filters.UserPostFilter;
import dataaccess.api.entities.UserPost;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface UserPostService {

    void createPost(UserPostDTO userPostDTO);

    void updateUser(UserPost userPost);

    UserPost getPostById(int id);

    List<UserPost> getPosts(UserPostFilter filter);
}
