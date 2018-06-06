package HouseIt.service.impl;

import HouseIt.dal.IBuildingDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Building;
import HouseIt.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("buildingService")
public class BuildingServiceImpl implements IBuildingService {

    private IBuildingDao buildingDao;

    @Autowired
    public BuildingServiceImpl(IBuildingDao buildingDao) throws HouseItServiceException {
        this.buildingDao = buildingDao;
    }

    public List<Building> getBuildings() throws HouseItServiceException {
        try {
            return buildingDao.getEntities(Building.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get buildings.", e);
        }
    }

    public Building findBuilding(Building building) throws HouseItServiceException {
        try {
            long id = building.getBuildingId();
            return buildingDao.findEntityById(Building.class, id);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not find building.", e);
        }
    }

    public boolean createBuilding(Building building) throws HouseItServiceException {
        boolean inserted = false;
        try {
            buildingDao.createEntity(building);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add building.", e);
        }
        return inserted;
    }

    public boolean updateBuilding(Building building) throws HouseItServiceException {
        boolean updated = false;
        try {
            buildingDao.updateEntity(building);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update building.", e);
        }
        return updated;
    }

    public boolean deleteBuilding(Building building) throws HouseItServiceException {
        try {
            Building b = findBuilding(building);
            if (b != null) {
                long id = b.getBuildingId();
                buildingDao.deleteEntity(Building.class, id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete building.", e);
        }
    }

}
