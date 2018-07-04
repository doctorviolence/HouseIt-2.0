package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;

import java.util.List;

public interface IManagerService {

    List<Building> getBuildings();

    List<Apartment> getApartmentsInBuilding(long buildingId);

    List<Tenant> getTenantsInApartment(long apartmentId);

    Tenant findTenant(long tenantId) throws MyEntityNotFoundException;

    void createTenant(Tenant tenant);

    void updateTenant(Tenant tenant);

    void deleteTenant(long tenantId) throws MyEntityNotFoundException;

    List<Case> getCases();

    List<Case> findCasesByTenantId(long tenantId);

    List<Case> getCasesByType(String caseType);

    List<Case> getCasesByFixDate();

    List<CaseMessage> getMessagesByCase(long caseNo);

    CaseMessage findMessage(long messageNo) throws MyEntityNotFoundException;

    void createMessage(CaseMessage caseMessage);

    void deleteMessage(long messageNo) throws MyEntityNotFoundException;

}
