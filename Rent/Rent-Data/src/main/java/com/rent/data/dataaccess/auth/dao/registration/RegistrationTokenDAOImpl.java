package com.rent.data.dataaccess.auth.dao.registration;

import com.rent.data.dataaccess.auth.dao.AuthDAO;
import com.rent.data.dataaccess.auth.entities.registration.RegistrationToken;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Duck on 10/28/2016.
 */
@Repository
public class RegistrationTokenDAOImpl extends AuthDAO implements RegistrationTokenDAO {

    public boolean saveRegistrationToken(RegistrationToken token) {
        return this.save(token);
    }

    @Transactional(readOnly = true)
    public RegistrationToken getTokenByString(String token) {
        Criteria criteria = getSession().createCriteria(RegistrationToken.class);

        criteria.add(Restrictions.eq("token", token));

        return (RegistrationToken) criteria.uniqueResult();
    }

    public boolean removeToken(RegistrationToken registrationToken) {
        return this.delete(registrationToken);
    }
}
