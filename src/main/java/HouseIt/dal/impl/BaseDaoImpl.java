package HouseIt.dal.impl;

import HouseIt.dal.IBaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class BaseDaoImpl<T extends Serializable> implements IBaseDao<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public List<T> getEntities(Class<T> entity) {
        return getCurrentSession().createQuery("FROM " + entity.getName()).list();
    }

    @SuppressWarnings("unchecked")
    public T findEntityById(Class<T> entity, long id) {
        return (T) getCurrentSession().get(entity, id);
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
