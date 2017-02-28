package com.rent.api.services.rent;

import com.rent.api.dao.listing.ListingRepository;
import com.rent.api.dao.rent.ConfirmedRentalsRepository;
import com.rent.api.dao.rent.RentRequestsRepository;
import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.rent.ConfirmedRentals;
import com.rent.api.entities.rent.RentRequests;
import com.rent.api.utility.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by duck on 2/28/17.
 */
@Service("rentService")
public class RentServiceImpl implements RentService {

    @Autowired
    private RentRequestsRepository rentRequestsRepository;

    @Autowired
    private ConfirmedRentalsRepository confirmedRentalsRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Transactional
    public void requestRent(int listingId) throws Exception {
        Listing listing = listingRepository.findById(listingId);
        if(!listing.isActive())
            throw new Exception("Listing is not available for rental.");

        RentRequests rentRequests = new RentRequests(listingId, UserSecurity.getUserId());

        rentRequestsRepository.save(rentRequests);
    }

    @Transactional(readOnly = true)
    public List<RentRequests> getRentRequests(int listingId) throws Exception {
        confirmListingOwnership(listingId);

        return rentRequestsRepository.findByListingId(listingId);
    }

    @Transactional(readOnly = true)
    public List<RentRequests> getRentRequests() {
        return rentRequestsRepository.findByRequestingUser(UserSecurity.getUserId());
    }

    @Transactional
    public void acceptRentRequest(int listingId, int requestId) throws Exception {
        confirmListingOwnership(listingId);

        RentRequests rentRequests = rentRequestsRepository.findById(requestId);
        rentRequests.setAccepted(true);
        rentRequestsRepository.save(rentRequests);
    }

    @Transactional
    public void confirmRentRequest(int requestId) throws Exception {
        // Verify user made the rental request
        RentRequests rentRequests = rentRequestsRepository.findById(requestId);
        if(rentRequests.getRequestingUser() != UserSecurity.getUserId())
            throw new Exception("User did not make rental request.");
        else if (!rentRequests.isAccepted())
            throw new Exception("Rental request has not been accepted.");

        // Deactivate the listing
        Listing listing = listingRepository.findById(rentRequests.getListingId());
        listing.setActive(false);
        listingRepository.save(listing);

        // Create listing and rented mapping
        ConfirmedRentals confirmedRentals = new ConfirmedRentals(rentRequests.getListingId(), UserSecurity.getUserId());
        confirmedRentalsRepository.save(confirmedRentals);

        // Remove the rental request
        // TODO: Remove all rental requests for the listing?
        // TODO: Restrict user from activating a listing if it is currently rented
        rentRequestsRepository.delete(rentRequests);
    }

    private void confirmListingOwnership(int listingId) throws Exception {
        // Verify requesting user has ownership of listing
        Listing listing = listingRepository.findById(listingId);
        if(listing.getUserId() != UserSecurity.getUserId())
            throw new Exception("User does not own listing.");
    }

}
