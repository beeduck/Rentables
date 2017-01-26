package com.rent.data.dataaccess.api.dao.listing;

import com.rent.data.dataaccess.api.entities.listing.ListingImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Asad on 11/28/2016.
 */
public interface ListingImageDAO {

    ListingImage uploadImage(ListingImage listingImage);

    ListingImage getImageById(String uuid);

    List<ListingImage> getImageByListingId(int listingId);

    ListingImage deleteImage(String uuid);
}