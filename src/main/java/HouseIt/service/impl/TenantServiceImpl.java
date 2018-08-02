package HouseIt.service.impl;

import HouseIt.dal.ITenantDao;
import HouseIt.entities.Tenant;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("tenantService")
@Transactional
public class TenantServiceImpl implements ITenantService {

    @Autowired
    private ITenantDao tenantDao;

    public List<Tenant> getTenants() {
        return tenantDao.getEntities(Tenant.class);
    }

    public List<Tenant> getTenantsInApartment(long apartmentId) {
        return tenantDao.getTenantsInFlat(apartmentId);
    }

    public Tenant findTenant(long id) throws MyEntityNotFoundException {
        Tenant t = tenantDao.findEntityById(Tenant.class, id);
        if (t == null) {
            throw new MyEntityNotFoundException(String.format("Tenant with ID %s not found.", id));
        }
        return t;
    }

    public void createTenant(Tenant tenant) {
        tenantDao.createEntity(tenant);
    }

    public void updateTenant(Tenant tenant) throws MyEntityNotFoundException {
        Tenant t = findTenant(tenant.getTenantId());
        if (t != null) {
            tenantDao.updateEntity(tenant);
        }
    }

    public void deleteTenant(long id) throws MyEntityNotFoundException {
        Tenant t = findTenant(id);
        if (t != null) {
            tenantDao.deleteEntity(Tenant.class, id);
        }
    }

}