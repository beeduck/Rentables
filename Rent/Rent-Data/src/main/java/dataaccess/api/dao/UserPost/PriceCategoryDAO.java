package dataaccess.api.dao.UserPost;

import dataaccess.api.entities.PriceCategory;

import java.util.List;

/**
 * Created by Asad on 11/4/2016.
 */

public interface PriceCategoryDAO {
    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryById(int id);
}
