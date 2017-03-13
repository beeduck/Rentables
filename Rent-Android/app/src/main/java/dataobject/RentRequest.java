package dataobject;

import com.rentables.testcenter.util.RentRequestRouteOptions;

import java.util.List;

/**
 * Created by Asad on 3/9/2017.
 */

public class RentRequest {

    private int listingId;
    private int requestingUser;
    private boolean isAccepted;
    private RentRequestRouteOptions option;
    private List<RentRequest> rentRequests;

    public RentRequestRouteOptions getOption() {
        return option;
    }

    public void setOption(RentRequestRouteOptions option) {
        this.option = option;
    }

    public int getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(int requestingUser) {
        this.requestingUser = requestingUser;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public List<RentRequest> getRentRequests() {
        return rentRequests;
    }

    public void setRentRequests(List<RentRequest> rentRequests) {
        this.rentRequests = rentRequests;
    }

}
