package com.rent.api.dao.listing;

import com.rent.api.entities.listing.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Asad on 2/10/2017.
 */
public interface ListingImageRepository extends JpaRepository<ListingImage, Long> {

    ListingImage getImageById(String uuid);

    List<ListingImage> getImageByListingId(int listingId);
}
