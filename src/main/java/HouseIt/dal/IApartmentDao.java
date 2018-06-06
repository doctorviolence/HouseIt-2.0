package HouseIt.dal;

import HouseIt.model.Apartment;

import java.util.List;

public interface IApartmentDao extends IBaseDao<Apartment> {

    List<Apartment> getApartmentsInBuilding(long buildingId);

}
