package com.rent.api.services.listing;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.rent.api.dao.listing.ListingRepository;
import com.rent.api.dao.user.UserInfoRepository;
import com.rent.api.dto.listing.ListingDTO;
import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.listing.QListing;
import com.rent.api.entities.user.UserInfo;
import com.rent.api.utility.RelevanceEngine;
import com.rent.api.utility.security.UserSecurity;
import com.rent.utility.filters.ListingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */

@Service("listingService")
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Transactional
    public Listing createListing(ListingDTO listingDTO) {
        Listing listing = new Listing();

        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setPrice(listingDTO.getPrice());
        listing.setPriceCategoryId(listingDTO.getPriceCategoryId());

        // TODO: Double check that this works
        UserInfo userInfo = userInfoRepository.findByUsername(UserSecurity.getUsername());
        listing.setUserId(userInfo.getId());

        listingRepository.save(listing);
        return listing;
    }

    public void updateListing(Listing listing) {
        listingRepository.save(listing);
    }

    public Listing getListingById(int id) {
        return listingRepository.findById(id);
    }

    public List<Listing> getListings(ListingFilter filter) {
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

        Iterable<Listing> lists = listingRepository.findAll(booleanBuilder);
        List<Listing> list = Lists.newArrayList(lists);
        if(filter.getKeywords().length > 0) {
            list = RelevanceEngine.sortByRelevance(list,filter.getKeywords());
        }
        return list;
    }
}
