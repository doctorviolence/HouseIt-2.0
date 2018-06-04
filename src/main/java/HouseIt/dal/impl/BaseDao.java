package HouseIt.dal.impl;

import HouseIt.dal.IBaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseDao<T extends Serializable> implements IBaseDao<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public Set<T> getEntities(Class<T> entity) {
        List<T> list = getCurrentSession().createQuery("FROM " + entity.getName()).list();

        if (list != null) {
            return new HashSet<T>(list);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public T findEntityById(Class<T> entity, long id) {
        return (T) getCurrentSession().get(entity.getClass(), id);
    }


    public void createEntity(final T entity) {
        if (entity != null) {
            getCurrentSession().saveOrUpdate(entity);
        }
    }

    public void updateEntity(final T entity) {
        if (entity != null) {
            getCurrentSession().merge(entity);
        }
    }

    public void deleteEntity(Class<T> entity, long id) {
        final T object = findEntityById(entity, id);

        if (object != null) {
            getCurrentSession().delete(object);
        }
    }

}
