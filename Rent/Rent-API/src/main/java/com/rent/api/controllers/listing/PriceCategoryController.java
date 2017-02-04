package com.rent.api.controllers.listing;

import com.rent.api.services.listing.PriceCategoryService;
import com.rent.api.entities.listing.PriceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */

@RestController
@RequestMapping("/price-categories")
public class PriceCategoryController {

    @Autowired
    PriceCategoryService priceCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PriceCategory> getAllPriceCategories() {
        return priceCategoryService.getAllPriceCategories();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PriceCategory getPriceCategoryById(@PathVariable("id") int id) {
        return priceCategoryService.getPriceCategoryById(id);
    }
}
