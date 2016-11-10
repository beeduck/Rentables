package dataaccess.api.dao.UserPost;


import dataaccess.api.dao.AbstractDAO;
import dataaccess.api.entities.UserPost;
import Utilities.Filters.UserPostFilter;
import org.hibernate.Criteria;
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
        return null;
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
