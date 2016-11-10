package dataaccess.api.dao;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Duck on 10/27/2016.
 */
//@ComponentScan("Configuration")
public abstract class AbstractDAO {

    @Autowired
    @Qualifier("apiSessionFactory")
    private SessionFactory sessionFactory;

    protected final Session getSession() { return sessionFactory.getCurrentSession(); }

    private final int BATCH_SIZE = 50;

    // TODO: Add filtering
    @Transactional(readOnly = true)
    protected <T> List<T> query(Class <T> tClass) {
        final Criteria criteria = getSession().createCriteria(tClass);

        return criteria.list();
    }

    protected final <T> boolean save(List<T> entities) throws HibernateException {
        if(entities != null) {
            int count = 0;
            for (Object entity : entities) {
                save(entity);
                count++;
                if(count % this.BATCH_SIZE == 0) {
                    getSession().flush();
                    getSession().clear();
                }
            }
            return true;
        }

        return false;
    }

    protected final boolean saveOrUpdate(Object entity) throws HibernateException {
        if(entity != null) {
            try {
                getSession().saveOrUpdate(entity);
            } catch(NonUniqueObjectException e) {
                getSession().merge(entity);
            }
            return true;
        }

        return false;
    }

    protected final boolean save(Object entity) throws HibernateException {
        if(entity != null) {
            try {
                getSession().save(entity);
            } catch(NonUniqueObjectException e) {
                getSession().merge(entity);
            }
            return true;
        }

        return false;
    }

    protected final boolean update(Object entity) throws HibernateException {
        getSession().update(entity);
        return true;
    }

    protected final boolean delete(Object entity) throws HibernateException {
        getSession().delete(entity);
        return true;
    }
}
