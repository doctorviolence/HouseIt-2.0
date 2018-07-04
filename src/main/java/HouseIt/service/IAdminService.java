package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;

import java.util.List;

public interface IAdminService {

    List<Building> getBuildings();

    Building findBuilding(long buildingId) throws MyEntityNotFoundException;

    void createBuilding(Building building);

    void updateBuilding(Building building);

    void deleteBuilding(long buildingId) throws MyEntityNotFoundException;

    List<Apartment> getApartmentsInBuilding(long buildingId);

    Apartment findApartment(long apartmentId) throws MyEntityNotFoundException;

    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment);

    void deleteApartment(long apartmentId) throws MyEntityNotFoundException;

    List<Manager> getManagers();

    Manager findManager(long managerId) throws MyEntityNotFoundException;

    void createManager(Manager manager);

    void updateManager(Manager manager);

    void deleteManager(long apartmentId) throws MyEntityNotFoundException;

    User findUser(long userId) throws MyEntityNotFoundException;

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(long userId) throws MyEntityNotFoundException;

}