package dataAccess.dao.UserPost;

import dataAccess.entities.UserPost;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface UserPostDAO {

    List<UserPost> getPostsByUserId(int userId);

    List<UserPost> getPostByKeywords(String[] keywords);

    UserPost getPostById(int id);

    UserPost createPost(UserPost userPost);

    boolean updatePost(UserPost userPost);
}
