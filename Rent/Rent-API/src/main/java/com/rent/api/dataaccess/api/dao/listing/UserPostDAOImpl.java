package com.rent.api.dataaccess.api.dao.listing;

import com.rent.api.dataaccess.api.dao.ApiDAO;
import com.rent.api.dataaccess.api.entities.listing.UserPost;
import com.rent.utility.filters.UserPostFilter;
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
    public List<UserPost> getPosts(UserPostFilter filter) {
        Criteria criteria = getSession().createCriteria(UserPost.class);
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
    public UserPost getPostById(int id) {
        Criteria criteria = getSession().createCriteria(UserPost.class);
        criteria.add(Restrictions.eq("id",id));
        return (UserPost)criteria.uniqueResult();
    }

    @Transactional
    public UserPost createPost(UserPost userPost) {
        if(this.save(userPost)) {
            return userPost;
        }
        return null;
    }

    @Transactional
    public boolean updatePost(UserPost userPost) {
        return this.update(userPost);
    }
}
