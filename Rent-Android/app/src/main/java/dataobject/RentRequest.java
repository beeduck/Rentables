package dataobject;

/**
 * Created by Asad on 3/9/2017.
 */

public class RentRequest {

    private int listingId;
    private int requestingUser;

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
}
