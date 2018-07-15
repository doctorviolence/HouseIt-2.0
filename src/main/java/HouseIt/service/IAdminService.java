package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;

import java.util.List;

public interface IAdminService {

    List<Building> getBuildings();

    Building findBuilding(long buildingId) throws MyEntityNotFoundException;

    void createBuilding(Building building);

    void updateBuilding(Building building) throws MyEntityNotFoundException;

    void deleteBuilding(long buildingId) throws MyEntityNotFoundException;

    List<Apartment> getApartmentsInBuilding(long buildingId);

    Apartment findApartment(long apartmentId) throws MyEntityNotFoundException;

    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment) throws MyEntityNotFoundException;

    void deleteApartment(long apartmentId) throws MyEntityNotFoundException;

    List<Manager> getManagers();

    Manager findManager(long managerId) throws MyEntityNotFoundException;

    void createManager(Manager manager);

    void updateManager(Manager manager) throws MyEntityNotFoundException;

    void deleteManager(long apartmentId) throws MyEntityNotFoundException;

    User findUser(long userId) throws MyEntityNotFoundException;

    void createUser(User user);

    void updateUser(User user) throws MyEntityNotFoundException;

    void deleteUser(long userId) throws MyEntityNotFoundException;

}