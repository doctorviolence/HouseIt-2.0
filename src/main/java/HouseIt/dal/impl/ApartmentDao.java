package HouseIt.dal.impl;

import HouseIt.dal.IApartmentDao;
import HouseIt.model.Apartment;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

@Repository
public class ApartmentDao extends BaseDao<Apartment> implements IApartmentDao {

    public Set<Apartment> getApartmentsInBuilding(long buildingId) {
        throw new NotImplementedException();
    }

}
