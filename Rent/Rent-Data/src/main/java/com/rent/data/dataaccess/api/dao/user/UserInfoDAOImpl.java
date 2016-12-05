package com.rent.data.dataaccess.api.dao.user;

import com.rent.data.dataaccess.api.dao.ApiDAO;
import com.rent.data.dataaccess.api.entities.user.UserInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Duck on 11/11/2016.
 */
@Repository("userInfoDao")
public class UserInfoDAOImpl extends ApiDAO implements UserInfoDAO {

//    @Transactional
    public boolean addUser(int userId, String username) {
        UserInfo userDetails = new UserInfo(userId, username);

        return this.save(userDetails);
    }

    @Transactional
    public boolean updateUser(UserInfo user) {
        return this.update(user);
    }

    @Transactional(readOnly = true)
    public UserInfo getUserByUsername(String username) {
        Criteria criteria = getSession().createCriteria(UserInfo.class);

        criteria.add(Restrictions.eq("username", username));  // TODO: Move to constants file

        return (UserInfo) criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public UserInfo getUserById(int userId) {
        Criteria criteria = getSession().createCriteria(UserInfo.class);

        criteria.add(Restrictions.eq("id", userId));

        return (UserInfo) criteria.uniqueResult();
    }
}
