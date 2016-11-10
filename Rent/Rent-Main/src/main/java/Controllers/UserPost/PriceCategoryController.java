package Controllers.UserPost;

import Services.UserPost.PriceCategoryService;
import dataaccess.api.entities.PriceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */

@RestController
@RequestMapping("/priceCategories")
@ComponentScan("Controllers")
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
