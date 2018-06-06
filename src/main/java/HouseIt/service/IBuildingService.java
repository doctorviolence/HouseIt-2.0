package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Building;

import java.util.List;

public interface IBuildingService {

    List<Building> getBuildings() throws HouseItServiceException;

    Building findBuilding(Building building) throws HouseItServiceException;

    boolean createBuilding(Building building) throws HouseItServiceException;

    boolean updateBuilding(Building building) throws HouseItServiceException;

    boolean deleteBuilding(Building building) throws HouseItServiceException;

}
