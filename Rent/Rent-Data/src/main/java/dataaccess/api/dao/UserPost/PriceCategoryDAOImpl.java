package dataaccess.api.dao.UserPost;

import dataaccess.api.dao.AbstractDAO;
import dataaccess.api.entities.PriceCategory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Asad on 11/4/2016.
 */

@Repository
public class PriceCategoryDAOImpl extends AbstractDAO implements PriceCategoryDAO {

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
