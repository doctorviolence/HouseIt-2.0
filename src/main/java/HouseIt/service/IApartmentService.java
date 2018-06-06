package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Apartment;

import java.util.List;

public interface IApartmentService {

    List<Apartment> getApartments() throws HouseItServiceException;

    List<Apartment> getApartmentsInBuilding(long buildingId) throws HouseItServiceException;

    Apartment findApartment(Apartment apartment) throws HouseItServiceException;

    boolean createApartment(Apartment apartment) throws HouseItServiceException;

    boolean updateApartment(Apartment apartment) throws HouseItServiceException;

    boolean deleteApartment(Apartment apartment) throws HouseItServiceException;

}
