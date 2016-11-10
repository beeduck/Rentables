package Services.UserPost;

import DTOEntities.UserPost.UserPostDTO;
import Utilities.DateUtils;
import Utilities.Filters.UserPostFilter;
import dataaccess.api.dao.UserPost.UserPostDAO;
import dataaccess.api.entities.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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

    public List<UserPost> getPostsByUserId(int userId) {
        return userPostDAO.getPostsByUserId(userId);
    }

    public List<UserPost> getPostsByKeywords(String[] keywords) {
        return userPostDAO.getPostByKeywords(keywords);
    }

    public UserPost getPostById(int id) {
        return userPostDAO.getPostById(id);
    }

    public List<UserPost> getPostsByPriceCategory(int id) {
        return userPostDAO.getPostsByPriceCategory(id);
    }

    public List<UserPost> getPostsByFilter(UserPostFilter filter) {
        return userPostDAO.getPostsByFilter(filter);
    }
}
