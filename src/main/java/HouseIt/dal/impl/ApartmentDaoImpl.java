package HouseIt.dal.impl;

import HouseIt.dal.IApartmentDao;
import HouseIt.entities.Apartment;
import HouseIt.entities.Building;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApartmentDaoImpl extends BaseDaoImpl<Apartment> implements IApartmentDao {

    @SuppressWarnings("unchecked")
    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        return (List<Apartment>) getCurrentSession().createCriteria(Apartment.class)
                .add(Restrictions.eq("building.buildingId", buildingId))
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Apartment> getEmptyApartmentsInBuilding(long buildingId) {
        return (List<Apartment>) getCurrentSession().createCriteria(Apartment.class)
                .add(Restrictions.eq("building.buildingId", buildingId))
                .add(Restrictions.isEmpty("tenants"))
                .list();
    }

    public Apartment createApartment(Apartment apartment) {
        getCurrentSession().saveOrUpdate(apartment);
        return apartment;
    }

}
