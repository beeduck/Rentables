package com.rent.api.dto.listing;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Asad on 11/29/2016.
 */
public class ListingImageDTO implements Serializable {

    @NotNull(message = "path required")
    private String path;

    @NotNull(message = "listingId required")
    private int listingId;

    public ListingImageDTO(){}

    public ListingImageDTO(String path, int listingId) {
        this.path = path;
        this.listingId = listingId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }
}