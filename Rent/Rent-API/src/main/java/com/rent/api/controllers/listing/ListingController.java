package com.rent.api.controllers.listing;

import com.rent.api.dto.listing.ListingDTO;
import com.rent.api.services.listing.ListingService;
import com.rent.api.entities.listing.Listing;
import com.rent.utility.Constants;
import com.rent.utility.filters.ListingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Asad on 11/1/2016.
 */

@RestController
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    ListingService listingService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Listing getListingById(@PathVariable("id") int id) {
        return listingService.getListingById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Listing> getListing(ListingFilter filter) {
        return listingService.getListings(filter);
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(method = RequestMethod.POST,
                    headers = "content-type=application/json")
    public Listing createListing(@Valid @RequestBody final ListingDTO listingDTO) {
        return listingService.createListing(listingDTO);
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(method = RequestMethod.PUT,
                    headers = "content-type=application/json")
    public void updateListing(@Valid @RequestBody final Listing post) {
        listingService.updateListing(post);
    }
}
