package HouseIt.dal;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T extends Serializable> {

    List<T> getEntities(Class<T> entity);

    T findEntityById(Class<T> entity, long id);

    void createEntity(final T entity);

    void updateEntity(final T entity);

    void deleteEntity(Class<T> entity, long id);

}
