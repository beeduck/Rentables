package com.rent.api.services.rent;

import com.rent.api.dao.listing.ListingRepository;
import com.rent.api.dao.rent.ConfirmedRentalsRepository;
import com.rent.api.dao.rent.PendingReturnRepository;
import com.rent.api.dao.rent.RentRequestsRepository;
import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.rent.ConfirmedRentals;
import com.rent.api.entities.rent.PendingReturn;
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
    private PendingReturnRepository pendingReturnRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Transactional
    public void requestRent(int listingId) throws Exception {
        Listing listing = listingRepository.findById(listingId);

        if(!listing.isActive())
            throw new Exception("Listing is not available for rental.");

        if(listing.getUserId() == UserSecurity.getUserId())
            throw new Exception("You cannot rent your own listing.");

        RentRequests rentRequests = new RentRequests(listingId, UserSecurity.getUserId());

        rentRequestsRepository.save(rentRequests);
    }

    @Transactional(readOnly = true)
    public List<RentRequests> getRentRequests(int listingId) throws Exception {
        confirmListingOwnership(listingId);

        return rentRequestsRepository.findByListingId(listingId);
    }

    @Transactional(readOnly = true)
    public List<RentRequests> getRequestingRentals() {
        return rentRequestsRepository.findByRequestingUser(UserSecurity.getUserId());
    }

    @Transactional(readOnly = true)
    public List<RentRequests> getRequestedRentals() {
        List<Integer> listingIds = listingRepository.findAllIdByUserId(UserSecurity.getUserId());

        return rentRequestsRepository.findByListingIdIn(listingIds);
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
        // TODO: Remove all rental requests for the listing? - deny them
        // TODO: Restrict user from activating a listing if it is currently rented
        rentRequestsRepository.delete(rentRequests);
    }

    @Transactional
    public void denyRentRequest(int listingId, int requestId) throws Exception {
        confirmListingOwnership(listingId);

        RentRequests rentRequests = rentRequestsRepository.findById(requestId);

        // TODO: First pass - add denied column and clean up denied listings after a day
        rentRequestsRepository.delete(rentRequests);
    }

    @Transactional
    public void cancelRentRequest(int requestId) throws Exception {
        // Verify user made the rental request
        RentRequests rentRequests = rentRequestsRepository.findById(requestId);
        if(rentRequests.getRequestingUser() != UserSecurity.getUserId())
            throw new Exception("User did not make rental request.");

        rentRequestsRepository.delete(rentRequests);
    }

    @Transactional(readOnly = true)
    public List<PendingReturn> getPendingReturnRequestsForRenter() {
        List<Integer> listingIds = listingRepository.findAllIdByUserId(UserSecurity.getUserId());
        return pendingReturnRepository.findByListingIdIn(listingIds);
    }


    @Transactional(readOnly = true)
    public List<PendingReturn> getPendingReturnRequestForRentee() {
        return pendingReturnRepository.findByReturningUserId(UserSecurity.getUserId());
    }


    @Transactional
    public void returnRentedListing(int rentalId) throws Exception {
        ConfirmedRentals confirmedRentals = confirmedRentalsRepository.findById(rentalId);

        if(confirmedRentals.getRentingUser() != UserSecurity.getUserId())
            throw new Exception("You are not the renting user.");

        PendingReturn pendingReturn = new PendingReturn(confirmedRentals.getListingId(), confirmedRentals.getRentingUser());

        pendingReturnRepository.save(pendingReturn);
    }

    @Transactional
    public void acceptRentedListingReturn(int pendingReturnId) throws Exception {
        PendingReturn pendingReturn = pendingReturnRepository.findById(pendingReturnId);

        if(!listingRepository.existsByUserIdAndId(UserSecurity.getUserId(), pendingReturn.getListingId()))
            throw new Exception("User does not own listing.");

        confirmedRentalsRepository.deleteByListingId(pendingReturn.getListingId());
        pendingReturnRepository.delete(pendingReturn);
    }


    @Transactional
    public void denyRentedListingReturn(int pendingReturnId) throws Exception {
        PendingReturn pendingReturn = pendingReturnRepository.findById(pendingReturnId);

        if(!listingRepository.existsByUserIdAndId(UserSecurity.getUserId(), pendingReturn.getListingId()))
            throw new Exception("User does not own listing.");

        pendingReturnRepository.delete(pendingReturn);
    }

    private void confirmListingOwnership(int listingId) throws Exception {
        // Verify requesting user has ownership of listing
        Listing listing = listingRepository.findById(listingId);
        if(listing.getUserId() != UserSecurity.getUserId())
            throw new Exception("User does not own listing.");
    }

    @Transactional(readOnly = true)
    public List<ConfirmedRentals> getRentedInListings() {
        return confirmedRentalsRepository.findByRentingUser(UserSecurity.getUserId());
    }

    @Transactional(readOnly = true)
    public List<ConfirmedRentals> getRentedOutListings() {
        List<Integer> listingIds = listingRepository.findAllIdByUserId(UserSecurity.getUserId());

        return confirmedRentalsRepository.findByListingIdIn(listingIds);
    }

}
