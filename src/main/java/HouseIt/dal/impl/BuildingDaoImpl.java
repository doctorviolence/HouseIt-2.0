package HouseIt.dal.impl;

import HouseIt.dal.IBuildingDao;
import HouseIt.entities.Building;
import org.springframework.stereotype.Repository;

@Repository
public class BuildingDaoImpl extends BaseDaoImpl<Building> implements IBuildingDao {

    public Building createBuilding(Building building) {
        getCurrentSession().saveOrUpdate(building);
        return building;
    }

}
