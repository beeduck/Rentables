package com.rent.api.services.listing;

import com.rent.api.dao.listing.ListingRepository;
import com.rent.api.dao.rent.ConfirmedRentalsRepository;
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

    @Autowired
    private ConfirmedRentalsRepository confirmedRentalsRepository;

    @Transactional
    public Listing createListing(ListingDTO listingDTO) {
        Listing listing = new Listing();

        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setPrice(listingDTO.getPrice());
        listing.setPriceCategoryId(listingDTO.getPriceCategoryId());

        UserInfo userInfo = userInfoRepository.findById(UserSecurity.getUserId());
        listing.setUserId(userInfo.getId());

        listingRepository.save(listing);
        return listing;
    }

    @Transactional
    public void updateListing(Listing listing) throws Exception {
        // Verify the listing being updated is owned by the user
        Listing listingVerify = listingRepository.findById(listing.getId());

        if(listingVerify.getUserId() != UserSecurity.getUserId())
            throw new Exception("Cannot change another users listing.");

        // TODO: Restrict active boolean if user is renting listing
        // TODO: Move to a listingDTO? - probably...
        listingRepository.save(listing);
    }

    @Transactional
    public void toggleListingActive(boolean active, int id) throws Exception {
        if(confirmedRentalsRepository.existsByListingId(id) && active)
            throw new Exception("Cannot advertise a listing that is being rented.");

        Listing listing = listingRepository.findById(id);

        if(listing.getUserId() != UserSecurity.getUserId())
            throw new Exception("Cannot change another users listing.");

        listing.setActive(active);
        listingRepository.save(listing);
    }

    public Listing getListingById(int id) {
        return listingRepository.findById(id);
    }

    @Transactional
    public void deleteListing(int id) throws Exception {
        // Verify the listing being deleted is owned by the user
        Listing listing = listingRepository.findById(id);

        if(listing.getUserId() != UserSecurity.getUserId())
            throw new Exception("Cannot delete another users listing.");

        if(confirmedRentalsRepository.existsByListingId(id))
            throw new Exception("Cannot delete a listing that is being rented.");

        listingRepository.delete(listing);
    }


    public List<Listing> getListings(ListingFilter filter) {
        List<Listing> list = listingRepository.findListsByFilter(filter);
        if(filter.getKeywords() != null) {
            list = RelevanceEngine.sortByRelevance(list,filter.getKeywords());
        }
        return list;
    }
}
