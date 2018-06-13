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

    public List<Building> getBuildings() {
        List<Building> buildings = buildingDao.getEntities(Building.class);

        if (buildings != null) {
            return buildings;
        } else {
            throw new ServiceLayerException();
        }
    }

    public Building findBuilding(long buildingId) {
        return buildingDao.findEntityById(Building.class, buildingId);
    }

    public void createBuilding(Building building) {
        if (building != null) {
            buildingDao.createEntity(building);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateBuilding(Building building) {
        Building b = findBuilding(building.getBuildingId());

        if (b != null) {
            buildingDao.updateEntity(building);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteBuilding(long id) {
        Building b = findBuilding(id);

        if (b != null) {
            buildingDao.deleteEntity(Building.class, id);
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        List<Apartment> apartments = apartmentDao.getApartmentsInBuilding(buildingId);

        if (apartments != null) {
            return apartments;
        } else {
            throw new ServiceLayerException();
        }
    }

    public Apartment findApartment(long apartmentId) {
        return apartmentDao.findEntityById(Apartment.class, apartmentId);
    }

    public void createApartment(Apartment apartment) {
        if (apartment != null) {
            apartmentDao.createEntity(apartment);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateApartment(Apartment apartment) {
        if (apartment != null) {
            apartmentDao.updateEntity(apartment);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteApartment(long id) {
        Apartment a = findApartment(id);

        if (a != null) {
            apartmentDao.deleteEntity(Apartment.class, id);
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Manager> getManagers() {
        List<Manager> managers = managerDao.getEntities(Manager.class);

        if (managers != null) {
            return managers;
        } else {
            throw new ServiceLayerException();
        }
    }

    public Manager findManager(long id) {
        return managerDao.findEntityById(Manager.class, id);
    }

    public void createManager(Manager manager) {
        if (manager != null) {
            managerDao.createEntity(manager);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateManager(Manager manager) {
        if (manager != null) {
            managerDao.updateEntity(manager);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteManager(long id) {
        Manager m = findManager(id);

        if (m != null) {
            managerDao.deleteEntity(Manager.class, id);
        } else {
            throw new ServiceLayerException();
        }
    }

    public User findUser(long id) {
        return userDao.findEntityById(User.class, id);
    }

    public void createUser(User user) {
        if (user != null) {
            userDao.createEntity(user);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateUser(User user) {
        if (user != null) {
            userDao.updateEntity(user);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteUser(long id) {
        User u = findUser(id);

        if (u != null) {
            userDao.deleteEntity(User.class, id);
        } else {
            throw new ServiceLayerException();
        }
    }

}