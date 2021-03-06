package com.rent.api.controllers.listing;

import com.rent.api.dto.listing.ListingDTO;
import com.rent.api.entities.listing.Listing;
import com.rent.api.services.listing.ListingService;
import com.rent.utility.Constants;
import com.rent.utility.filters.ListingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public Listing getListingById(@PathVariable("id") @NotNull Integer id) {
        return listingService.getListingById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Listing> getListing(ListingFilter filter) {
        return listingService.getListings(filter);
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(value = "/current-user", method = RequestMethod.GET)
    public List<Listing> getCurrentUsersListings() {
        return listingService.getCurrentUsersListings();
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(method = RequestMethod.POST,
                    headers = "content-type=application/json")
    public Listing createListing(@Valid @RequestBody final ListingDTO listingDTO) {
        return listingService.createListing(listingDTO);
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteListing(@PathVariable("id") @NotNull Integer id) throws Exception {
        listingService.deleteListing(id);
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(method = RequestMethod.PUT,
                    headers = "content-type=application/json")
    public void updateListing(@Valid @RequestBody final Listing listing) throws Exception {
        listingService.updateListing(listing);
    }

    @PreAuthorize(Constants.AUTH_ROLE_USER)
    @RequestMapping(value = "/{id}/active/{boolean}", method = RequestMethod.PUT)
    public void toggleListingActive(@PathVariable("id") @NotNull Integer id,
                                    @PathVariable("boolean") @NotNull Boolean active) throws Exception {
        listingService.toggleListingActive(active, id);
    }
}
