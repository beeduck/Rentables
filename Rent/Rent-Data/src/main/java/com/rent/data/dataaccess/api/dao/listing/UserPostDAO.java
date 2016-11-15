package com.rent.data.dataaccess.api.dao.listing;

import com.rent.data.dataaccess.api.entities.listing.UserPost;
import com.rent.utility.filters.UserPostFilter;

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
