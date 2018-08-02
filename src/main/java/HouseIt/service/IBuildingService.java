package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;

import java.util.List;

public interface IBuildingService {

    List<Building> getBuildings();

    Building findBuilding(long buildingId) throws MyEntityNotFoundException;

    void createBuilding(Building building);

    void updateBuilding(Building building) throws MyEntityNotFoundException;

    void deleteBuilding(long buildingId) throws MyEntityNotFoundException;

}