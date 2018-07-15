package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;
import HouseIt.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private IBuildingDao buildingDao;

    @Autowired
    private IApartmentDao apartmentDao;

    @Autowired
    private IManagerDao managerDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Building> getBuildings() {
        return buildingDao.getEntities(Building.class);
    }

    public Building findBuilding(long buildingId) throws MyEntityNotFoundException {
        Building b = buildingDao.findEntityById(Building.class, buildingId);
        if (b == null) {
            throw new MyEntityNotFoundException(String.format("Building with ID %s not found.", buildingId));
        }
        return b;
    }

    public void createBuilding(Building building) {
        buildingDao.createEntity(building);
    }

    public void updateBuilding(Building building) throws MyEntityNotFoundException {
        Building b = findBuilding(building.getBuildingId());
        if (b != null) {
            buildingDao.updateEntity(building);
        }
    }

    public void deleteBuilding(long id) throws MyEntityNotFoundException {
        Building b = findBuilding(id);
        if (b != null) {
            buildingDao.deleteEntity(Building.class, id);
        }
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        return apartmentDao.getApartmentsInBuilding(buildingId);
    }

    public Apartment findApartment(long apartmentId) throws MyEntityNotFoundException {
        Apartment a = apartmentDao.findEntityById(Apartment.class, apartmentId);
        if (a == null) {
            throw new MyEntityNotFoundException(String.format("Apartment with ID %s not found.", apartmentId));
        }
        return a;
    }

    public void createApartment(Apartment apartment) {
        apartmentDao.createEntity(apartment);
    }

    public void updateApartment(Apartment apartment) throws MyEntityNotFoundException {
        Apartment a = findApartment(apartment.getApartmentId());
        if (a != null) {
            apartmentDao.updateEntity(apartment);
        }
    }

    public void deleteApartment(long id) throws MyEntityNotFoundException {
        Apartment a = findApartment(id);
        if (a != null) {
            apartmentDao.deleteEntity(Apartment.class, id);
        }
    }

    public List<Manager> getManagers() {
        return managerDao.getEntities(Manager.class);
    }

    public Manager findManager(long id) throws MyEntityNotFoundException {
        Manager m = managerDao.findEntityById(Manager.class, id);
        if (m == null) {
            throw new MyEntityNotFoundException(String.format("Manager with ID %s not found.", id));
        }
        return m;
    }

    public void createManager(Manager manager) {
        managerDao.createEntity(manager);
    }

    public void updateManager(Manager manager) throws MyEntityNotFoundException {
        Manager m = findManager(manager.getManagerId());
        if (m != null) {
            managerDao.updateEntity(manager);
        }
    }

    public void deleteManager(long id) throws MyEntityNotFoundException {
        Manager m = findManager(id);
        if (m != null) {
            managerDao.deleteEntity(Manager.class, id);
        }
    }

    public User findUser(long id) throws MyEntityNotFoundException {
        User u = userDao.findEntityById(User.class, id);
        if (u == null) {
            throw new MyEntityNotFoundException(String.format("User with ID %s not found.", id));
        }
        return u;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createEntity(user);
    }

    public void updateUser(User user) throws MyEntityNotFoundException {
        User u = findUser(user.getId());
        if (u != null) {
            userDao.updateEntity(user);
        }
    }

    public void deleteUser(long id) throws MyEntityNotFoundException {
        User u = findUser(id);
        if (u != null) {
            userDao.deleteEntity(User.class, id);
        }
    }

}