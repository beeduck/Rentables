package dataaccess.api.dao.UserPost;

import dataaccess.api.entities.UserPost;
import Utilities.Filters.UserPostFilter;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface UserPostDAO {

    List<UserPost> getPosts(UserPostFilter filter);

    UserPost getPostById(int id);

    UserPost createPost(UserPost userPost);

    boolean updatePost(UserPost userPost);
}
