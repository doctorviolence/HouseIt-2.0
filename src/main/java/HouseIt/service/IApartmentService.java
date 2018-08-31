package HouseIt.service;

import HouseIt.entities.Apartment;
import HouseIt.exception.MyEntityNotFoundException;

import java.util.List;

public interface IApartmentService {

    List<Apartment> getAllApartments();

    List<Apartment> getEmptyApartmentsInBuilding(long buildingId);

    List<Apartment> getApartmentsInBuilding(long buildingId);

    Apartment findApartment(long apartmentId) throws MyEntityNotFoundException;

    Apartment createApartment(Apartment apartment);

    void updateApartment(Apartment apartment) throws MyEntityNotFoundException;

    void deleteApartment(long apartmentId) throws MyEntityNotFoundException;

}
