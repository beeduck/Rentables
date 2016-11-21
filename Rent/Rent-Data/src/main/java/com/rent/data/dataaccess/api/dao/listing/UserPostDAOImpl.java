package com.rent.data.dataaccess.api.dao.listing;

import com.rent.data.dataaccess.api.entities.listing.Listing;
import com.rent.utility.filters.ListingFilter;
import com.rent.data.dataaccess.api.dao.ApiDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
@Repository
public class UserPostDAOImpl extends ApiDAO implements UserPostDAO {

    Logger logger = LoggerFactory.getLogger(UserPostDAO.class);

    @Transactional(readOnly = true)
    public List<Listing> getPosts(ListingFilter filter) {
        Criteria criteria = getSession().createCriteria(Listing.class);
        Conjunction conjunction = Restrictions.conjunction();
        if(filter.getId() > 0) {
            conjunction.add(Restrictions.eq("id", filter.getId()));
        }
        if(filter.getUserId() > 0) {
            conjunction.add(Restrictions.eq("userId", filter.getUserId()));
        }
        if(filter.getMaxPrice() > 0.0) {
            conjunction.add(Restrictions.le("price", filter.getMaxPrice()));
        }
        if(filter.getMinPrice() > 0.0) {
            conjunction.add(Restrictions.ge("price", filter.getMinPrice()));
        }
        if(filter.getPriceCategoryId() > 0) {
            conjunction.add(Restrictions.eq("priceCategoryId", filter.getPriceCategoryId()));
        }
        if(filter.getKeywords() != null) {
            Disjunction disjunction = Restrictions.disjunction();
            for (String e : filter.getKeywords()) {
                disjunction.add(Restrictions.like("title", e + "%"));
                disjunction.add(Restrictions.like("title", e));
                disjunction.add(Restrictions.like("title", "%" + e));
                disjunction.add(Restrictions.like("title", "%" + e + "%"));
            }
            conjunction.add(disjunction);
        }
        criteria.add(conjunction);
        logger.info("stuff");
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public Listing getPostById(int id) {
        Criteria criteria = getSession().createCriteria(Listing.class);
        criteria.add(Restrictions.eq("id",id));
        return (Listing)criteria.uniqueResult();
    }

    @Transactional
    public Listing createPost(Listing listing) {
        if(this.save(listing)) {
            return listing;
        }
        return null;
    }

    @Transactional
    public boolean updatePost(Listing listing) {
        return this.update(listing);
    }
}
