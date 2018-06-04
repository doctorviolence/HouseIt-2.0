package HouseIt.dal;

import java.io.Serializable;
import java.util.Set;

public interface IBaseDao<T extends Serializable> {

    Set<T> getEntities(Class<T> entity);

    T findEntityById(Class<T> entity, long id);

    void createEntity(final T entity);

    void updateEntity(final T entity);

    void deleteEntity(Class<T> entity, long id);

}
