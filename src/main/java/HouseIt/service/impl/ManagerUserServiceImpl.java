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

    public List<Building> getBuildings() {
        List<Building> buildings = buildingDao.getEntities(Building.class);

        if (buildings != null) {
            return buildings;
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        List<Apartment> apartments = apartmentDao.getApartmentsInBuilding(buildingId);

        if (apartments != null) {
            return apartments;
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Tenant> getTenantsInApartment(long apartmentId) {
        List<Tenant> tenants = tenantDao.getTenantsInFlat(apartmentId);

        if (tenants != null) {
            return tenants;
        } else {
            throw new ServiceLayerException();
        }
    }

    public Tenant findTenant(long id) {
        return tenantDao.findEntityById(Tenant.class, id);
    }

    public void createTenant(Tenant tenant) {
        if (tenant != null) {
            tenantDao.createEntity(tenant);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateTenant(Tenant tenant) {
        if (tenant != null) {
            tenantDao.updateEntity(tenant);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteTenant(long id) {
        Tenant t = findTenant(id);

        if (t != null) {
            tenantDao.deleteEntity(Tenant.class, id);
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Case> getCases() {
        List<Case> cases = caseDao.getEntities(Case.class);

        if (cases != null) {
            return cases;
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Case> findCasesByTenantId(long tenantId) {
        List<Case> cases = caseDao.findCasesByTenantId(tenantId);
        if (cases != null) {
            return cases;
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Case> getCasesByType(String caseType) {
        List<Case> cases = caseDao.getCasesByType(caseType);

        if (cases != null) {
            return cases;
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<Case> getCasesByFixDate() {
        List<Case> cases = caseDao.getCasesByFixDate();

        if (cases != null) {
            return cases;
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<CaseMessage> getMessagesByCase(long caseNo) {
        List<CaseMessage> caseMessages = caseMessageDao.getCaseMessagesByCase(caseNo);

        if (caseMessages != null) {
            return caseMessages;
        } else {
            throw new ServiceLayerException();
        }
    }

    public CaseMessage findMessage(long no) {
        return caseMessageDao.findEntityById(CaseMessage.class, no);
    }

    public void createMessage(CaseMessage caseMessage) {
        if (caseMessage != null) {
            caseMessageDao.createEntity(caseMessage);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteMessage(long no) {
        CaseMessage cm = findMessage(no);

        if (cm != null) {
            caseMessageDao.deleteEntity(CaseMessage.class, no);
        } else {
            throw new ServiceLayerException();
        }
    }

}