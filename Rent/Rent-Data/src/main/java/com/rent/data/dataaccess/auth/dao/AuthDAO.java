package com.rent.data.dataaccess.auth.dao;

import com.rent.data.dataaccess.AbstractBaseDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Duck on 11/11/2016.
 */
public class AuthDAO extends AbstractBaseDAO {

    @Autowired
    @Qualifier("authSessionFactory")
    private SessionFactory sessionFactory;

    protected final Session getSession() { return sessionFactory.getCurrentSession(); }

    private final int BATCH_SIZE = 50;

    // TODO: Add filtering
    @Transactional(readOnly = true)
    protected <T> List<T> query(Class <T> tClass) {
        return super.query(tClass, getSession());
    }

    protected final <T> boolean save(List<T> entities) throws HibernateException {
        return super.save(entities, getSession());
    }

    protected final boolean saveOrUpdate(Object entity) throws HibernateException {
        return super.saveOrUpdate(entity, getSession());
    }

    protected final boolean save(Object entity) throws HibernateException {
        return super.save(entity, getSession());
    }

    protected final boolean update(Object entity) throws HibernateException {
        return super.update(entity, getSession());
    }

    protected final boolean delete(Object entity) throws HibernateException {
        return super.delete(entity, getSession());
    }
}
