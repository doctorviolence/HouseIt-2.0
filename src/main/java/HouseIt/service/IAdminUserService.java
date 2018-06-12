package HouseIt.service;

import HouseIt.exception.ServiceLayerException;
import HouseIt.model.*;

import java.util.List;

public interface IAdminUserService {

    List<Building> getBuildings() throws ServiceLayerException;

    void createBuilding(Building building) throws ServiceLayerException;

    void updateBuilding(Building building) throws ServiceLayerException;

    void deleteBuilding(Building building) throws ServiceLayerException;

    List<Apartment> getApartmentsInBuilding(long buildingId) throws ServiceLayerException;

    void createApartment(Apartment apartment) throws ServiceLayerException;

    void updateApartment(Apartment apartment) throws ServiceLayerException;

    void deleteApartment(Apartment apartment) throws ServiceLayerException;

    List<Manager> getManagers() throws ServiceLayerException;

    void createManager(Manager manager) throws ServiceLayerException;

    void updateManager(Manager manager) throws ServiceLayerException;

    void deleteManager(Manager manager) throws ServiceLayerException;

    void createUser(User user) throws ServiceLayerException;

    void updateUser(User user) throws ServiceLayerException;

    void deleteUser(User user) throws ServiceLayerException;

}
