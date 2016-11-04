package dataAccess.dao.UserPost;

import dataAccess.entities.PriceCategory;

import java.util.List;

/**
 * Created by Asad on 11/4/2016.
 */

public interface PriceCategoryDAO {
    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryById(int id);
}
