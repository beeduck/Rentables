package com.rent.api.dataaccess;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Duck on 11/11/2016.
 */
public abstract class AbstractBaseDAO {
    private final int BATCH_SIZE = 50;

    // TODO: Add filtering
    @Transactional(readOnly = true)
    protected <T> List<T> query(Class <T> tClass, Session session) {
        final Criteria criteria = session.createCriteria(tClass);

        return criteria.list();
    }

    protected final <T> boolean save(List<T> entities, Session session) throws HibernateException {
        if(entities != null) {
            int count = 0;
            for (Object entity : entities) {
                save(entity, session);
                count++;
                if(count % this.BATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            return true;
        }

        return false;
    }

    protected final boolean saveOrUpdate(Object entity, Session session) throws HibernateException {
        if(entity != null) {
            try {
                session.saveOrUpdate(entity);
            } catch(NonUniqueObjectException e) {
                session.merge(entity);
            }
            return true;
        }

        return false;
    }

    protected final boolean save(Object entity, Session session) throws HibernateException {
        if(entity != null) {
            try {
                session.save(entity);
            } catch(NonUniqueObjectException e) {
                session.merge(entity);
            }
            return true;
        }

        return false;
    }

    protected final boolean update(Object entity, Session session) throws HibernateException {
        session.update(entity);
        return true;
    }

    protected final boolean delete(Object entity, Session session) throws HibernateException {
        session.delete(entity);
        return true;
    }
}
