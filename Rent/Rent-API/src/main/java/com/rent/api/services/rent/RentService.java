package com.rent.api.services.rent;

import com.rent.api.entities.rent.ConfirmedRentals;
import com.rent.api.entities.rent.RentRequests;

import java.util.List;

/**
 * Created by duck on 2/28/17.
 */
public interface RentService {

        void requestRent(int listingId) throws Exception;
        List<RentRequests> getRentRequests(int listingId) throws Exception;
        List<RentRequests> getRequestingRentals();
        List<RentRequests> getRequestedRentals();
        void acceptRentRequest(int listingId, int requestId) throws Exception;
        void confirmRentRequest(int requestId) throws Exception;

        List<ConfirmedRentals> getRentedInListings();
        List<ConfirmedRentals> getRentedOutListings();
}
