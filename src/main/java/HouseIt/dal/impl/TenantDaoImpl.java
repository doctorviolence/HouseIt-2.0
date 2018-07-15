package HouseIt.dal.impl;

import HouseIt.dal.ITenantDao;
import HouseIt.entities.Tenant;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TenantDaoImpl extends BaseDaoImpl<Tenant> implements ITenantDao {

    @SuppressWarnings("unchecked")
    public List<Tenant> getTenantsInFlat(long apartmentId) {
        return (List<Tenant>) getCurrentSession().createCriteria(Tenant.class)
                .add(Restrictions.eq("apartment.apartmentId", apartmentId))
                .list();
    }

}
