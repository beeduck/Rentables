package dataaccess.dao;

import dataaccess.entities.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by duck on 10/25/16.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);

        criteria.add(Restrictions.eq("username", username));  // TODO: Move to constants file

        return (User) criteria.uniqueResult();
    }
}