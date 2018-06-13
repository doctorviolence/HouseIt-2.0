package HouseIt.service;

import HouseIt.model.*;

import java.util.List;

public interface IManagerUserService {

    List<Building> getBuildings();

    List<Apartment> getApartmentsInBuilding(long buildingId);

    List<Tenant> getTenantsInApartment(long apartmentId);

    Tenant findTenant(long tenantId);

    void createTenant(Tenant tenant);

    void updateTenant(Tenant tenant);

    void deleteTenant(long tenantId);

    List<Case> getCases();

    List<Case> findCasesByTenantId(long tenantId);

    List<Case> getCasesByType(String caseType);

    List<Case> getCasesByFixDate();

    List<CaseMessage> getMessagesByCase(long caseNo);

    CaseMessage findMessage(long messageNo);

    void createMessage(CaseMessage caseMessage);

    void deleteMessage(long messageNo);

}
