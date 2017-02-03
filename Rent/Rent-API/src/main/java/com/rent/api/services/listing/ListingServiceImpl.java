package com.rent.api.services.listing;

import com.rent.api.dao.listing.ListingRepository;
import com.rent.api.dao.user.UserInfoRepository;
import com.rent.api.dto.listing.ListingDTO;
import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.user.UserInfo;
import com.rent.api.utility.RelevanceEngine;
import com.rent.api.utility.security.UserSecurity;
import com.rent.utility.filters.ListingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */

@Service("listingService")
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Transactional
    public Listing createListing(ListingDTO listingDTO) {
        Listing listing = new Listing();

        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setPrice(listingDTO.getPrice());
        listing.setPriceCategoryId(listingDTO.getPriceCategoryId());

        UserInfo userInfo = userInfoRepository.findByUsername(UserSecurity.getUsername());
        listing.setUserId(userInfo.getId());

        listingRepository.save(listing);
        return listing;
    }

    public void updateListing(Listing listing) {
        listingRepository.save(listing);
    }

    public Listing getListingById(int id) {
        return listingRepository.findById(id);
    }

    public List<Listing> getListings(ListingFilter filter) {
        List<Listing> list = listingRepository.findListsByFilter(filter);
        if(filter.getKeywords().length > 0) {
            list = RelevanceEngine.sortByRelevance(list,filter.getKeywords());
        }
        return list;
    }
}
