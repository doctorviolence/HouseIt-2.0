package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;
import HouseIt.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("buildingService")
@Transactional
public class BuildingServiceImpl implements IBuildingService {

    @Autowired
    private IBuildingDao buildingDao;

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

    public Building createBuilding(Building building) {
        return buildingDao.createBuilding(building);
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

}