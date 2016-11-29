package com.rent.api.services.listing;

import com.rent.api.dto.listing.ListingImageDTO;
import com.rent.data.dataaccess.api.entities.listing.ListingImage;

import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */
public interface ListingImageService {

    ListingImage uploadImage(ListingImageDTO listingImageDTO);

    ListingImage getImageById(int id);

    List<ListingImage> getImageByListingId(int listingId);
}
