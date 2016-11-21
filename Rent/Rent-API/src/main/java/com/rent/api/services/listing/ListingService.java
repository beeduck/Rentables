package com.rent.api.services.listing;

import com.rent.api.dto.listing.ListingDTO;
import com.rent.data.dataaccess.api.entities.listing.Listing;
import com.rent.utility.filters.ListingFilter;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface ListingService {

    void createListing(ListingDTO listingDTO);

    void updateListing(Listing listing);

    Listing getListingById(int id);

    List<Listing> getListings(ListingFilter filter);
}
