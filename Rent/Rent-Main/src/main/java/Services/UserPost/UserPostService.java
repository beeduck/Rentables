package Services.UserPost;

import DTOEntities.UserPost.UserPostDTO;
import dataaccess.api.entities.UserPost;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface UserPostService {

    void createPost(UserPostDTO userPostDTO);

    void updateUser(UserPost userPost);

    List<UserPost> getPostsByUserId(int userId);

    List<UserPost> getPostsByKeywords(String[] keywords);

    UserPost getPostById(int id);

    List<UserPost> getPostsByPriceCategory(int id);
}
