package com.rent.api.services.listing;

import com.rent.api.entities.listing.PriceCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */

@Service("priceCategoryService")
public class PriceCategoryServiceImpl implements PriceCategoryService {

//    @Autowired
//    PriceCategoryDAO priceCategoryDAO;

    public List<PriceCategory> getAllPriceCategories() {
//        return priceCategoryDAO.getAllPriceCategories();
        return null;
    }

    public PriceCategory getPriceCategoryById(int id) {
//        return priceCategoryDAO.getPriceCategoryById(id);
        return null;
    }
}
