package com.rent.data.dataaccess.api.dao.listing;

import com.rent.data.dataaccess.api.dao.ApiDAO;
import com.rent.api.entities.listing.PriceCategory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Asad on 11/4/2016.
 */

@Repository
public class PriceCategoryDAOImpl extends ApiDAO implements PriceCategoryDAO {

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<PriceCategory> getAllPriceCategories() {
        Criteria criteria = getSession().createCriteria(PriceCategory.class);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public PriceCategory getPriceCategoryById(int id) {
        Criteria criteria = getSession().createCriteria(PriceCategory.class);
        criteria.add(Restrictions.eq("id",id));
        return (PriceCategory)criteria.uniqueResult();
    }
}
