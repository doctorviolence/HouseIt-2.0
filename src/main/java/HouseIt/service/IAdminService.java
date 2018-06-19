package HouseIt.service;

import HouseIt.model.*;

import java.util.List;

public interface IAdminService {

    List<Building> getBuildings();

    Building findBuilding(long buildingId);

    void createBuilding(Building building);

    void updateBuilding(Building building);

    void deleteBuilding(long buildingId);

    List<Apartment> getApartmentsInBuilding(long buildingId);

    Apartment findApartment(long apartmentId);

    void createApartment(Apartment apartment);

    void updateApartment(Apartment apartment);

    void deleteApartment(long apartmentId);

    List<Manager> getManagers();

    Manager findManager(long managerId);

    void createManager(Manager manager);

    void updateManager(Manager manager);

    void deleteManager(long apartmentId);

    User findUser(long userId);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(long userId);

}