package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Manager;

import java.util.List;

public interface IManagerService {

    List<Manager> getManagers() throws HouseItServiceException;

    Manager findManager(Manager manager) throws HouseItServiceException;

    boolean createManager(Manager manager) throws HouseItServiceException;

    boolean updateManager(Manager manager) throws HouseItServiceException;

    boolean deleteManager(Manager manager) throws HouseItServiceException;

}
