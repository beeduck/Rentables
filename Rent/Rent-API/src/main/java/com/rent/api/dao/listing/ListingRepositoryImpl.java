package com.rent.api.dao.listing;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.listing.QListing;
import com.rent.utility.filters.ListingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by duck on 1/30/17.
 */
public class ListingRepositoryImpl implements ListingRepositoryCustom {

    @Autowired
    ListingRepository listingRepository;

    public List<Listing> findListsByFilter(ListingFilter filter) {

        QListing qListing = QListing.listing;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(filter.getId() > 0) {
            booleanBuilder.and(qListing.id.eq(filter.getId()));
        }
        if(filter.getUserId() > 0) {
            booleanBuilder.and(qListing.userId.eq(filter.getUserId()));
        }
        if(filter.getMaxPrice() > 0.0) {
            booleanBuilder.and(qListing.price.loe(filter.getMaxPrice()));
        }
        if(filter.getMinPrice() > 0.0) {
            booleanBuilder.and(qListing.price.goe(filter.getMinPrice()));
        }
        if(filter.getPriceCategoryId() > 0) {
            booleanBuilder.and(qListing.priceCategoryId.eq(filter.getPriceCategoryId()));
        }
        if(filter.getKeywords() != null) {
            BooleanBuilder titleBuilder = new BooleanBuilder();
            for (String e : filter.getKeywords()) {
                titleBuilder.or(qListing.title.containsIgnoreCase(e));
                titleBuilder.or(qListing.description.containsIgnoreCase(e));
            }
            booleanBuilder.and(titleBuilder);
        }

        // Return only active listings for searching
        booleanBuilder.and(qListing.active.eq(true));

        Iterable<Listing> lists = listingRepository.findAll(booleanBuilder);
        return Lists.newArrayList(lists);
    }
}
