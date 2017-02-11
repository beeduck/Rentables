package com.rent.api.dao.listing;

import com.rent.api.entities.listing.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Asad on 2/10/2017.
 */
public interface ListingImageRepository extends JpaRepository<ListingImage, Long> {

    ListingImage findByImageUUID(String uuid);

    List<ListingImage> findAllByListingId(int listingId);

    void deleteByImageUUID(String uuid);

    void deleteByListingId(int listingId);
}
