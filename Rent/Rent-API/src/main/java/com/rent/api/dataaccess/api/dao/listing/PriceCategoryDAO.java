package com.rent.api.dataaccess.api.dao.listing;


import com.rent.api.dataaccess.api.entities.listing.PriceCategory;

import java.util.List;

/**
 * Created by Asad on 11/4/2016.
 */

public interface PriceCategoryDAO {
    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryById(int id);
}