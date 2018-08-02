package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;

import java.util.List;

public interface IManagerService {

    List<Manager> getManagers();

    Manager findManager(long managerId) throws MyEntityNotFoundException;

    void createManager(Manager manager);

    void updateManager(Manager manager) throws MyEntityNotFoundException;

    void deleteManager(long apartmentId) throws MyEntityNotFoundException;

}
