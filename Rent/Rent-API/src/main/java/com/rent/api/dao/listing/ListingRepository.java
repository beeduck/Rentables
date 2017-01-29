package com.rent.api.dao.listing;

import com.rent.api.entities.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Duck on 1/28/2017.
 */
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // TODO: Figure this out
//    List<Listing> findPosts(ListingFilter filter);

    Listing findById(int id);
}
