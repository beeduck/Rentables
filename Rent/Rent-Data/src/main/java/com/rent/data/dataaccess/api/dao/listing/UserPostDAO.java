package com.rent.data.dataaccess.api.dao.listing;

import com.rent.data.dataaccess.api.entities.listing.Listing;
import com.rent.utility.filters.ListingFilter;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
public interface UserPostDAO {

    List<Listing> getPosts(ListingFilter filter);

    Listing getPostById(int id);

    Listing createPost(Listing listing);

    boolean updatePost(Listing listing);
}
