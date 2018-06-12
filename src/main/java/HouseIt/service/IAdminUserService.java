package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.*;

import java.util.List;

public interface IAdminUserService {

    List<Building> getBuildings() throws HouseItServiceException;

    void createBuilding(Building building) throws HouseItServiceException;

    void updateBuilding(Building building) throws HouseItServiceException;

    void deleteBuilding(Building building) throws HouseItServiceException;

    List<Apartment> getApartmentsInBuilding(long buildingId) throws HouseItServiceException;

    void createApartment(Apartment apartment) throws HouseItServiceException;

    void updateApartment(Apartment apartment) throws HouseItServiceException;

    void deleteApartment(Apartment apartment) throws HouseItServiceException;

    List<Manager> getManagers() throws HouseItServiceException;

    void createManager(Manager manager) throws HouseItServiceException;

    void updateManager(Manager manager) throws HouseItServiceException;

    void deleteManager(Manager manager) throws HouseItServiceException;

    void createUser(User user) throws HouseItServiceException;

    void updateUser(User user) throws HouseItServiceException;

    void deleteUser(User user) throws HouseItServiceException;

}
