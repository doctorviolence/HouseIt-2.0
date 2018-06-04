package HouseIt.dal;

import HouseIt.model.Apartment;

import java.util.Set;

public interface IApartmentDao extends IBaseDao<Apartment> {

    Set<Apartment> getApartmentsInBuilding(long buildingId);

}
