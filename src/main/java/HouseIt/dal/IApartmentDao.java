package HouseIt.dal;

import HouseIt.entities.Apartment;

import java.util.List;

public interface IApartmentDao extends IBaseDao<Apartment> {

    List<Apartment> getApartmentsInBuilding(long buildingId);

    List<Apartment> getEmptyApartmentsInBuilding(long buildingId);

    Apartment createApartment(Apartment apartment);

}
