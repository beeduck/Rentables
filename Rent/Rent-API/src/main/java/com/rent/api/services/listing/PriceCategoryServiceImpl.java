package com.rent.api.services.listing;

import com.rent.api.dataaccess.api.dao.listing.PriceCategoryDAO;
import com.rent.api.dataaccess.api.entities.listing.PriceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */

@Service("priceCategoryService")
public class PriceCategoryServiceImpl implements PriceCategoryService {

    @Autowired
    PriceCategoryDAO priceCategoryDAO;

    public List<PriceCategory> getAllPriceCategories() {
        return priceCategoryDAO.getAllPriceCategories();
    }

    public PriceCategory getPriceCategoryById(int id) {
        return priceCategoryDAO.getPriceCategoryById(id);
    }
}
