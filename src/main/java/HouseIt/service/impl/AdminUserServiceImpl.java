package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.*;
import HouseIt.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminUserService")
public class AdminUserServiceImpl implements IAdminUserService {

    private IBuildingDao buildingDao;
    private IApartmentDao apartmentDao;
    private ITenantDao tenantDao;
    private ICaseDao caseDao;
    private ICaseMessageDao caseMessageDao;
    private IManagerDao managerDao;

    @Autowired
    public AdminUserServiceImpl(IBuildingDao buildingDao, IApartmentDao apartmentDao, ITenantDao tenantDao, ICaseDao caseDao,
                                ICaseMessageDao caseMessageDao, IManagerDao managerDao) throws HouseItServiceException {
        this.buildingDao = buildingDao;
        this.apartmentDao = apartmentDao;
        this.tenantDao = tenantDao;
        this.caseDao = caseDao;
        this.caseMessageDao = caseMessageDao;
        this.managerDao = managerDao;
    }

    public List<Building> getBuildings() throws HouseItServiceException {
        try {
            return buildingDao.getEntities(Building.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get buildings.", e);
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
        boolean deleted = false;
        try {
            long id = building.getBuildingId();
            buildingDao.deleteEntity(Building.class, id);
            deleted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete building.", e);
        }
        return deleted;
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) throws HouseItServiceException {
        try {
            return apartmentDao.getApartmentsInBuilding(buildingId);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get the building's apartments.", e);
        }
    }

    public boolean createApartment(Apartment apartment) throws HouseItServiceException {
        boolean inserted = false;
        try {
            apartmentDao.createEntity(apartment);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add apartment.", e);
        }
        return inserted;
    }

    public boolean updateApartment(Apartment apartment) throws HouseItServiceException {
        boolean updated = false;
        try {
            apartmentDao.updateEntity(apartment);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update apartment.", e);
        }
        return updated;
    }

    public boolean deleteApartment(Apartment apartment) throws HouseItServiceException {
        boolean deleted = false;
        try {
            long id = apartment.getApartmentId();
            apartmentDao.deleteEntity(Apartment.class, id);
            deleted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete apartment.", e);
        }
        return deleted;
    }

    public List<Tenant> getTenantsInApartment(long apartmentId) throws HouseItServiceException {
        try {
            return tenantDao.getTenantsInFlat(apartmentId);
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

    public List<Case> getCases() throws HouseItServiceException {
        try {
            return caseDao.getEntities(Case.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException {
        try {
            return caseDao.findCasesByTenantId(tenantId);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Case> getCasesByType(String caseType) throws HouseItServiceException {
        try {
            return caseDao.getCasesByType(caseType);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Case> getCasesByFixDate() throws HouseItServiceException {
        try {
            return caseDao.getCasesByFixDate();
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Manager> getManagers() throws HouseItServiceException {
        try {
            return managerDao.getEntities(Manager.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get managers.", e);
        }
    }

    public boolean createManager(Manager manager) throws HouseItServiceException {
        boolean inserted = false;
        try {
            managerDao.createEntity(manager);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add manager.", e);
        }
        return inserted;
    }

    public boolean updateManager(Manager manager) throws HouseItServiceException {
        boolean updated = false;
        try {
            managerDao.updateEntity(manager);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update manager.", e);
        }
        return updated;
    }

    public boolean deleteManager(Manager manager) throws HouseItServiceException {
        boolean deleted = false;
        try {
            long id = manager.getManagerId();
            managerDao.deleteEntity(Manager.class, id);
            deleted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete manager.", e);
        }
        return deleted;
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException {
        try {
            return caseMessageDao.getCaseMessagesByCase(caseNo);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get case messages.", e);
        }
    }

    public boolean createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException {
        boolean inserted = false;
        try {
            caseMessageDao.createEntity(caseMessage);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add case message.", e);
        }
        return inserted;
    }

    public boolean deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException {
        boolean deleted = false;
        try {
            long no = caseMessage.getMessageNo();
            caseMessageDao.deleteEntity(CaseMessage.class, no);
            deleted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete case message.", e);
        }
        return deleted;
    }

}
