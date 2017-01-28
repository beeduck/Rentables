package com.rent.api.services.listing;

import com.rent.api.dto.listing.ListingDTO;
import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.user.UserInfo;
import com.rent.api.utility.RelevanceEngine;
import com.rent.api.utility.security.UserSecurity;
import com.rent.utility.DateUtils;
import com.rent.utility.filters.ListingFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */

@Service("listingService")
public class ListingServiceImpl implements ListingService {

//    @Autowired
//    private ListingDAO listingDAO;
//
//    @Autowired
//    private UserInfoDAO userInfoDAO;

    @Transactional
    public Listing createListing(ListingDTO listingDTO) {
        Listing listing = new Listing();

        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setPrice(listingDTO.getPrice());
        listing.setPriceCategoryId(listingDTO.getPriceCategoryId());

//        UserInfo userInfo = userInfoDAO.getUserByUsername(UserSecurity.getUsername());
        UserInfo userInfo = null;
        listing.setUserId(userInfo.getId());

        Timestamp timeStamp = DateUtils.getCurrentUtcTimestamp();
        listing.setCreateDate(timeStamp);
        listing.setLastEditDate(timeStamp);

//        listingDAO.createPost(listing);
        return listing;
    }

    public void updateListing(Listing listing) {
        Timestamp timestamp = DateUtils.getCurrentUtcTimestamp();
        listing.setLastEditDate(timestamp);
//        listingDAO.updatePost(listing);
    }

    public Listing getListingById(int id) {
//        return listingDAO.getPostById(id);
        return null;
    }

    public List<Listing> getListings(ListingFilter filter) {
//        List<Listing> list = listingDAO.getPosts(filter);
        List<Listing> list = null;
        if(filter.getKeywords().length > 0) {
            list = RelevanceEngine.sortByRelevance(list,filter.getKeywords());
        }
        return list;
    }
}
