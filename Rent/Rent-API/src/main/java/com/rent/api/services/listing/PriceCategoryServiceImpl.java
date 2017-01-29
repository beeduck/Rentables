package com.rent.api.services.listing;

import com.rent.api.dao.listing.PriceCategoryRepository;
import com.rent.api.entities.listing.PriceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */

@Service("priceCategoryService")
public class PriceCategoryServiceImpl implements PriceCategoryService {

    @Autowired
    PriceCategoryRepository priceCategoryRepository;

    public List<PriceCategory> getAllPriceCategories() {
        return priceCategoryRepository.findAll();
    }

    public PriceCategory getPriceCategoryById(int id) {
        return priceCategoryRepository.findById(id);
    }
}
