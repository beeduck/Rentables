package com.rent.api.services.listing;

import com.rent.api.dto.listing.UserPostDTO;
import com.rent.api.util.RelevanceEngine;
import com.rent.utility.DateUtils;
import com.rent.utility.filters.UserPostFilter;
import com.rent.data.dataaccess.api.dao.listing.UserPostDAO;
import com.rent.data.dataaccess.api.entities.listing.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Asad on 10/31/2016.
 */

@Service("userPostService")
public class UserPostServiceImpl implements UserPostService {

    @Autowired
    private UserPostDAO userPostDAO;

    public void createPost(UserPostDTO userPostDTO) {
        UserPost post = new UserPost();
        post.setTitle(userPostDTO.getTitle());
        post.setDescription(userPostDTO.getDescription());
        post.setUserId(userPostDTO.getUserId());
        post.setPrice(userPostDTO.getPrice());
        post.setPriceCategoryId(userPostDTO.getPriceCategoryId());
        Timestamp timeStamp = DateUtils.getCurrentUtcTimestamp();
        post.setCreateDate(timeStamp);
        post.setLastEditDate(timeStamp);
        userPostDAO.createPost(post);
    }

    public void updateUser(UserPost userPost) {
        Timestamp timestamp = DateUtils.getCurrentUtcTimestamp();
        userPost.setLastEditDate(timestamp);
        userPostDAO.updatePost(userPost);
    }

    public UserPost getPostById(int id) {
        return userPostDAO.getPostById(id);
    }

    //I know how extremely inefficient this looks and probably is...working on better solution
    public List<UserPost> getPosts(UserPostFilter filter) {
        List<UserPost> postList =  userPostDAO.getPosts(filter);
        if(filter.getKeywords() != null) {
            return RelevanceEngine.sortByRelevance(postList,filter);
        }
        return postList;
    }

    public int getPostCount() {
        return userPostDAO.getPostCount();
    }
}
