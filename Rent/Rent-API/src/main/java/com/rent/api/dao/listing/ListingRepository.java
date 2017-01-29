package com.rent.api.dao.listing;

import com.rent.api.entities.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Duck on 1/28/2017.
 */
public interface ListingRepository extends JpaRepository<Listing, Long>, QueryDslPredicateExecutor {

    Listing findById(Integer id);
}
