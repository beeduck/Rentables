package com.rent.api.dao.listing;

import com.rent.api.entities.listing.PriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Duck on 1/28/2017.
 */
@Repository
public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {

    PriceCategory findById(int id);
}
