package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.ServiceLayerException;
import HouseIt.model.*;
import HouseIt.service.IManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("managerUserService")
public class ManagerUserServiceImpl implements IManagerUserService {

    private IBuildingDao buildingDao;
    private IApartmentDao apartmentDao;
    private ITenantDao tenantDao;
    private ICaseDao caseDao;
    private ICaseMessageDao caseMessageDao;

    @Autowired
    public ManagerUserServiceImpl(IBuildingDao buildingDao, IApartmentDao apartmentDao, ITenantDao tenantDao, ICaseDao caseDao, ICaseMessageDao caseMessageDao) {
        this.buildingDao = buildingDao;
        this.apartmentDao = apartmentDao;
        this.tenantDao = tenantDao;
        this.caseDao = caseDao;
        this.caseMessageDao = caseMessageDao;
    }

    public List<Building> getBuildings() throws ServiceLayerException {
        try {
            return buildingDao.getEntities(Building.class);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get buildings.", e);
        }
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) throws ServiceLayerException {
        try {
            return apartmentDao.getApartmentsInBuilding(buildingId);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get the building's apartments.", e);
        }
    }

    public List<Tenant> getTenantsInApartment(long apartmentId) throws ServiceLayerException {
        try {
            return tenantDao.getTenantsInFlat(apartmentId);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get tenants.", e);
        }
    }

    public Tenant findTenant(Tenant tenant) throws ServiceLayerException {
        try {
            long id = tenant.getTenantId();
            return tenantDao.findEntityById(Tenant.class, id);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not find tenant.", e);
        }
    }

    public void createTenant(Tenant tenant) throws ServiceLayerException {
        try {
            tenantDao.createEntity(tenant);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add tenant.", e);
        }
    }

    public void updateTenant(Tenant tenant) throws ServiceLayerException {
        try {
            tenantDao.updateEntity(tenant);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update tenant.", e);
        }
    }

    public void deleteTenant(Tenant tenant) throws ServiceLayerException {
        try {
            Tenant t = findTenant(tenant);
            if (t != null) {
                long id = t.getTenantId();
                tenantDao.deleteEntity(Tenant.class, id);
            }
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete tenant.", e);
        }
    }

    public List<Case> getCases() throws ServiceLayerException {
        try {
            return caseDao.getEntities(Case.class);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get cases.", e);
        }
    }

    public List<Case> findCasesByTenantId(long tenantId) throws ServiceLayerException {
        try {
            return caseDao.findCasesByTenantId(tenantId);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get cases.", e);
        }
    }

    public List<Case> getCasesByType(String caseType) throws ServiceLayerException {
        try {
            return caseDao.getCasesByType(caseType);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get cases.", e);
        }
    }

    public List<Case> getCasesByFixDate() throws ServiceLayerException {
        try {
            return caseDao.getCasesByFixDate();
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get cases.", e);
        }
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) throws ServiceLayerException {
        try {
            return caseMessageDao.getCaseMessagesByCase(caseNo);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get case messages.", e);
        }
    }

    public void createCaseMessage(CaseMessage caseMessage) throws ServiceLayerException {
        try {
            caseMessageDao.createEntity(caseMessage);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add case message.", e);
        }
    }

    public void deleteCaseMessage(CaseMessage caseMessage) throws ServiceLayerException {
        try {
            long no = caseMessage.getMessageNo();
            caseMessageDao.deleteEntity(CaseMessage.class, no);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete case message.", e);
        }
    }

}
