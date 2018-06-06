package HouseIt.dal.impl;

import HouseIt.dal.IApartmentDao;
import HouseIt.model.Apartment;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApartmentDaoImplImpl extends BaseDaoImpl<Apartment> implements IApartmentDao {

    @SuppressWarnings("unchecked")
    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        return (List<Apartment>) getCurrentSession().createCriteria(Apartment.class)
                .add(Restrictions.eq("building.buildingId", buildingId))
                .list();
    }

}
