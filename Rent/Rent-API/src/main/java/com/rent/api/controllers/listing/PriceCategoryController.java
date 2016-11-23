package com.rent.api.controllers.listing;

import com.rent.api.dataaccess.api.entities.listing.PriceCategory;
import com.rent.api.services.listing.PriceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */

@RestController
@RequestMapping("/priceCategories")
public class PriceCategoryController {

    @Autowired
    PriceCategoryService priceCategoryService;

    @RequestMapping(value = "/getAllPriceCategories", method = RequestMethod.GET)
    public List<PriceCategory> getAllPriceCategories() {
        return priceCategoryService.getAllPriceCategories();
    }

    @RequestMapping(value = "/getPriceCategoryById/{id}", method = RequestMethod.GET)
    public PriceCategory getPriceCategoryById(@PathVariable("id") int id) {
        return priceCategoryService.getPriceCategoryById(id);
    }
}
