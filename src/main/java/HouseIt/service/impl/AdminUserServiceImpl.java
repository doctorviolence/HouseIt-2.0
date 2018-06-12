package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.ServiceLayerException;
import HouseIt.model.*;
import HouseIt.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminUserService")
public class AdminUserServiceImpl implements IAdminUserService {

    private IBuildingDao buildingDao;
    private IApartmentDao apartmentDao;
    private IManagerDao managerDao;
    private IUserDao userDao;

    @Autowired
    public AdminUserServiceImpl(IBuildingDao buildingDao, IApartmentDao apartmentDao, IManagerDao managerDao, IUserDao userDao) {
        this.buildingDao = buildingDao;
        this.apartmentDao = apartmentDao;
        this.managerDao = managerDao;
        this.userDao = userDao;
    }

    public List<Building> getBuildings() throws ServiceLayerException {
        try {
            return buildingDao.getEntities(Building.class);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get buildings.", e);
        }
    }

    public void createBuilding(Building building) throws ServiceLayerException {
        try {
            buildingDao.createEntity(building);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add building.", e);
        }
    }

    public void updateBuilding(Building building) throws ServiceLayerException {
        try {
            buildingDao.updateEntity(building);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update building.", e);
        }
    }

    public void deleteBuilding(Building building) throws ServiceLayerException {
        try {
            long id = building.getBuildingId();
            buildingDao.deleteEntity(Building.class, id);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete building.", e);
        }
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) throws ServiceLayerException {
        try {
            return apartmentDao.getApartmentsInBuilding(buildingId);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get the building's apartments.", e);
        }
    }

    public void createApartment(Apartment apartment) throws ServiceLayerException {
        try {
            apartmentDao.createEntity(apartment);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add apartment.", e);
        }
    }

    public void updateApartment(Apartment apartment) throws ServiceLayerException {
        try {
            apartmentDao.updateEntity(apartment);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update apartment.", e);
        }
    }

    public void deleteApartment(Apartment apartment) throws ServiceLayerException {
        try {
            long id = apartment.getApartmentId();
            apartmentDao.deleteEntity(Apartment.class, id);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete apartment.", e);
        }
    }

    public List<Manager> getManagers() throws ServiceLayerException {
        try {
            return managerDao.getEntities(Manager.class);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get managers.", e);
        }
    }

    public void createManager(Manager manager) throws ServiceLayerException {
        try {
            managerDao.createEntity(manager);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add manager.", e);
        }
    }

    public void updateManager(Manager manager) throws ServiceLayerException {
        try {
            managerDao.updateEntity(manager);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update manager.", e);
        }
    }

    public void deleteManager(Manager manager) throws ServiceLayerException {
        try {
            long id = manager.getManagerId();
            managerDao.deleteEntity(Manager.class, id);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete manager.", e);
        }
    }

    public void createUser(User user) throws ServiceLayerException {
        try {
            userDao.createEntity(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add new user.", e);
        }
    }

    public void updateUser(User user) throws ServiceLayerException {
        try {
            userDao.updateEntity(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update user details.", e);
        }
    }

    public void deleteUser(User user) throws ServiceLayerException {
        try {
            userDao.deleteUser(user);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete user.", e);
        }
    }

}
