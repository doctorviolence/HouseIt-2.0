package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;
import HouseIt.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private IBuildingDao buildingDao;

    @Autowired
    private IApartmentDao apartmentDao;

    @Autowired
    private ITenantDao tenantDao;

    @Autowired
    private ICaseDao caseDao;

    @Autowired
    private ICaseMessageDao caseMessageDao;

    public List<Building> getBuildings() {
        return buildingDao.getEntities(Building.class);
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        return apartmentDao.getApartmentsInBuilding(buildingId);
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

    public void updateTenant(Tenant tenant) {
        tenantDao.updateEntity(tenant);
    }

    public void deleteTenant(long id) throws MyEntityNotFoundException {
        Tenant t = findTenant(id);
        if (t != null) {
            tenantDao.deleteEntity(Tenant.class, id);
        }
    }

    public List<Case> getCases() {
        return caseDao.getEntities(Case.class);
    }

    public List<Case> findCasesByTenantId(long tenantId) {
        return caseDao.findCasesByTenantId(tenantId);
    }

    public List<Case> getCasesByType(String caseType) {
        return caseDao.getCasesByType(caseType);
    }

    public List<Case> getCasesByFixDate() {
        return caseDao.getCasesByFixDate();
    }

    public List<CaseMessage> getMessagesByCase(long caseNo) {
        return caseMessageDao.getCaseMessagesByCase(caseNo);
    }

    public CaseMessage findMessage(long no) throws MyEntityNotFoundException {
        CaseMessage cm = caseMessageDao.findEntityById(CaseMessage.class, no);
        if (cm == null) {
            throw new MyEntityNotFoundException(String.format("Message no. %s not found.", no));
        }
        return cm;
    }

    public void createMessage(CaseMessage caseMessage) {
        caseMessageDao.createEntity(caseMessage);
    }

    public void deleteMessage(long no) throws MyEntityNotFoundException {
        CaseMessage cm = findMessage(no);
        if (cm != null) {
            caseMessageDao.deleteEntity(CaseMessage.class, no);
        }
    }

}