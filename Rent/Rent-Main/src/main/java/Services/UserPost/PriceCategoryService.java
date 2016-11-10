package Services.UserPost;

import dataaccess.api.entities.PriceCategory;

import java.util.List;

/**
 * Created by Asad on 11/7/2016.
 */
public interface PriceCategoryService {

    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryById(int id);
}
