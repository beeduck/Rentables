package com.rent.api.services.listing;

import com.rent.api.dto.listing.ListingDTO;
import com.rent.data.dataaccess.api.dao.user.UserInfoDAO;
import com.rent.data.dataaccess.api.entities.listing.Listing;
import com.rent.data.dataaccess.api.entities.user.UserInfo;
import com.rent.utility.DateUtils;
import com.rent.utility.filters.ListingFilter;
import com.rent.data.dataaccess.api.dao.listing.UserPostDAO;
import com.rent.utility.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */

@Service("listingService")
public class ListingServiceImpl implements ListingService {

    @Autowired
    private UserPostDAO userPostDAO;

    @Autowired
    private UserInfoDAO userInfoDAO;

    @Transactional
    public void createListing(ListingDTO listingDTO) {
        Listing post = new Listing();

        post.setTitle(listingDTO.getTitle());
        post.setDescription(listingDTO.getDescription());
        post.setPrice(listingDTO.getPrice());
        post.setPriceCategoryId(listingDTO.getPriceCategoryId());

        UserInfo userInfo = userInfoDAO.getUserByUsername(Security.getUsername());
        post.setUserId(userInfo.getId());

        Timestamp timeStamp = DateUtils.getCurrentUtcTimestamp();
        post.setCreateDate(timeStamp);
        post.setLastEditDate(timeStamp);

        userPostDAO.createPost(post);
    }

    public void updateListing(Listing listing) {
        Timestamp timestamp = DateUtils.getCurrentUtcTimestamp();
        listing.setLastEditDate(timestamp);
        userPostDAO.updatePost(listing);
    }

    public Listing getListingById(int id) {
        return userPostDAO.getPostById(id);
    }

    public List<Listing> getListings(ListingFilter filter) {
        return userPostDAO.getPosts(filter);
    }
}
