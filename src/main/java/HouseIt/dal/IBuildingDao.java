package HouseIt.dal;

import HouseIt.entities.Building;

public interface IBuildingDao extends IBaseDao<Building> {

    Building createBuilding(Building building);

}
