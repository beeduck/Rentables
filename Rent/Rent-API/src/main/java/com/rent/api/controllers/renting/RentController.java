package com.rent.api.controllers.renting;

import com.rent.api.entities.rent.RentRequests;
import com.rent.api.services.rent.RentService;
import com.rent.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by duck on 2/28/17.
 */
@RestController
@RequestMapping("/rent")
@PreAuthorize(Constants.AUTH_ROLE_USER)
public class RentController {

    @Autowired
    private RentService rentService;

    @RequestMapping(value = "/request/{listing-id}", method = RequestMethod.POST)
    public void requestRent(@PathVariable("listing-id") @NotNull Integer listingId) throws Exception {
        rentService.requestRent(listingId);
    }

    @RequestMapping(value = "/request/{listing-id}", method = RequestMethod.GET)
    public List<RentRequests> getRentRequests(@PathVariable("listing-id") @NotNull Integer listingId) throws Exception {
        return rentService.getRentRequests(listingId);
    }

    @RequestMapping(value = "/request/{listing-id}/accept/{request-id}", method = RequestMethod.PUT)
    public void acceptRentRequest(@PathVariable("listing-id") @NotNull Integer listingId,
                                  @PathVariable("request-id") @NotNull Integer requestId) throws Exception {
        rentService.acceptRentRequest(listingId, requestId);
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public List<RentRequests> getRentRequestsForUser() {
        return rentService.getRentRequests();
    }

    @RequestMapping(value = "/request/{request-id}", method = RequestMethod.PUT)
    public void confirmRentRequest(@PathVariable("request-id") @NotNull Integer requestId) throws Exception {
        rentService.confirmRentRequest(requestId);
    }

}