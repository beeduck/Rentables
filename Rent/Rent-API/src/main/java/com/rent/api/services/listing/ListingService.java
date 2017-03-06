package com.rent.api.services.listing;

import com.rent.api.dto.listing.ListingDTO;
import com.rent.api.entities.listing.Listing;
import com.rent.utility.filters.ListingFilter;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface ListingService {

    Listing createListing(ListingDTO listingDTO);

    void updateListing(Listing listing) throws Exception;

    void toggleListingActive(boolean active, int id) throws Exception;

    Listing getListingById(int id);

    void deleteListing(int id) throws Exception;

    List<Listing> getListings(ListingFilter filter);

    List<Listing> getCurrentUsersListings();
}
