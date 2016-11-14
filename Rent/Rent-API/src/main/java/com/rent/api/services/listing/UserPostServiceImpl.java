package com.rent.api.services.listing;

import com.rent.api.dto.listing.UserPostDTO;
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
        Map<UserPost,Integer> hashmap = new HashMap<>();
        for(UserPost e : postList) {
            String[] postSplit = e.getTitle().split("\\s+");
            int count = 0;
            for(String s : postSplit) {
                for(int i=0; i<filter.getKeywords().length; i++) {
                    if(s.equalsIgnoreCase(filter.getKeywords()[i])) {
                        count++;
                    }
                }
            }
            hashmap.put(e,count);
        }
        Set<Map.Entry<UserPost, Integer>> set = hashmap.entrySet();
        List<Map.Entry<UserPost, Integer>> list = new ArrayList<>(set);
        list.sort((o1,o2)->o2.getValue()-o1.getValue());
        postList = new ArrayList<>();
        for(Map.Entry<UserPost, Integer> e : list) {
            postList.add(e.getKey());
        }
        return postList;
    }
}
