package com.rent.api.dao.listing;

import com.rent.api.entities.listing.Listing;
import com.rent.utility.filters.ListingFilter;

import java.util.List;

/**
 * Created by duck on 1/30/17.
 */
public interface ListingRepositoryCustom {

    List<Listing> findListsByFilter(ListingFilter filter);
}
