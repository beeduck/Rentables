package com.rent.data.dataaccess.auth.dao.user;

import com.rent.data.dataaccess.auth.dao.AuthDAO;
import com.rent.data.dataaccess.auth.entities.user.UserDetails;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by duck on 10/25/16.
 */
@Repository
public class UserDetailsDAOImpl extends AuthDAO implements UserDetailsDAO {

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<UserDetails> getAllUsers() {
        Criteria criteria = getSession().createCriteria(UserDetails.class);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public UserDetails getUserByUsername(String username) {
        Criteria criteria = getSession().createCriteria(UserDetails.class);

        criteria.add(Restrictions.eq("username", username));  // TODO: Move to constants file

        return (UserDetails) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public UserDetails getUserById(int userId) {
        Criteria criteria = getSession().createCriteria(UserDetails.class);

        criteria.add(Restrictions.eq("id", userId));

        return (UserDetails) criteria.uniqueResult();
    }

    @Transactional
    public boolean updateUser(UserDetails user) {
        return this.update(user);
    }

//    @Transactional
    public UserDetails createUser(UserDetails user) {
        if (this.save(user)) {
            return user;
        }
        return null;
    }

    @Transactional
    public boolean toggleUserActivation(UserDetails user) {
        user.setActive(true);

        return this.updateUser(user);
    }
}