package dataAccess.dao.Registration;

import dataAccess.dao.AbstractDAO;
import dataAccess.entities.Registration.VerificationToken;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Duck on 10/28/2016.
 */
@Repository
public class VerificationTokenDAOImpl extends AbstractDAO implements VerificationTokenDAO {

    public boolean saveVerificationToken(VerificationToken token) {
        return this.save(token);
    }

    @Transactional(readOnly = true)
    public VerificationToken getTokenByString(String token) {
        Criteria criteria = getSession().createCriteria(VerificationToken.class);

        criteria.add(Restrictions.eq("token", token));

        return (VerificationToken) criteria.uniqueResult();
    }

    public boolean removeToken(VerificationToken verificationToken) {
        return this.delete(verificationToken);
    }
}
