package Services.UserPost;

import dataAccess.dao.UserPost.PriceCategoryDAO;
import dataAccess.entities.PriceCategory;
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
