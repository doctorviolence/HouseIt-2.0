package HouseIt.service.impl;

import HouseIt.dal.ITenantDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Tenant;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tenantService")
public class TenantServiceImpl implements ITenantService {

    private ITenantDao tenantDao;

    @Autowired
    public TenantServiceImpl(ITenantDao tenantDao) throws HouseItServiceException {
        this.tenantDao = tenantDao;
    }

    public List<Tenant> getTenants() throws HouseItServiceException {
        try {
            return tenantDao.getEntities(Tenant.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get tenants.", e);
        }
    }

    public Tenant findTenant(Tenant tenant) throws HouseItServiceException {
        try {
            long id = tenant.getTenantId();
            return tenantDao.findEntityById(Tenant.class, id);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not find tenant.", e);
        }
    }

    public boolean createTenant(Tenant tenant) throws HouseItServiceException {
        boolean inserted = false;
        try {
            tenantDao.createEntity(tenant);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add tenant.", e);
        }
        return inserted;
    }

    public boolean updateTenant(Tenant tenant) throws HouseItServiceException {
        boolean updated = false;
        try {
            tenantDao.updateEntity(tenant);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update tenant.", e);
        }
        return updated;
    }

    public boolean deleteTenant(Tenant tenant) throws HouseItServiceException {
        try {
            Tenant t = findTenant(tenant);
            if (t != null) {
                long id = t.getTenantId();
                tenantDao.deleteEntity(Tenant.class, id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete tenant.", e);
        }
    }

}
