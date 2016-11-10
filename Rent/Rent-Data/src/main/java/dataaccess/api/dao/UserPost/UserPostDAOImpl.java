package dataaccess.api.dao.UserPost;


import dataaccess.api.dao.AbstractDAO;
import dataaccess.api.entities.UserPost;
import Utilities.Filters.UserPostFilter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Asad on 10/31/2016.
 */
@Repository
public class UserPostDAOImpl extends AbstractDAO implements UserPostDAO {

    @Transactional(readOnly = true)
    public List<UserPost> getPostsByUserId(int userId) {
        Criteria criteria = getSession().createCriteria(UserPost.class);
        criteria.add(Restrictions.eq("userId",userId));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<UserPost> getPostByKeywords(String[] keywords) {
        Criteria criteria = getSession().createCriteria(UserPost.class);
        Disjunction disjunction = Restrictions.disjunction();
        for (String e : keywords) {
            disjunction.add(Restrictions.like("title", e + "%"));
            disjunction.add(Restrictions.like("title", e));
            disjunction.add(Restrictions.like("title", "%" + e));
            disjunction.add(Restrictions.like("title", "%" + e + "%"));
        }
        criteria.add(disjunction);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<UserPost> getPostsByPriceCategory(int id) {
        Criteria criteria = getSession().createCriteria(UserPost.class);
        criteria.add(Restrictions.eq("priceCategoryId", id));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<UserPost> getPostsByFilter(UserPostFilter filter) {
        Criteria criteria = getSession().createCriteria(UserPost.class);
        Conjunction conjunction = Restrictions.conjunction();
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
        if(filter.getKeywords().length > 0) {
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
