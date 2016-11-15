package Services.UserPost;

import DTOEntities.UserPost.UserPostDTO;
import Utilities.Filters.UserPostFilter;
import com.rent.data.dataaccess.api.entities.listing.UserPost;

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
