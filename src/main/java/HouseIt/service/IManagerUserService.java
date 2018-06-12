package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.*;

import java.util.List;

public interface IManagerUserService {

    List<Building> getBuildings() throws HouseItServiceException;

    List<Apartment> getApartmentsInBuilding(long buildingId) throws HouseItServiceException;

    List<Tenant> getTenantsInApartment(long apartmentId) throws HouseItServiceException;

    Tenant findTenant(Tenant tenant) throws HouseItServiceException;

    void createTenant(Tenant tenant) throws HouseItServiceException;

    void updateTenant(Tenant tenant) throws HouseItServiceException;

    void deleteTenant(Tenant tenant) throws HouseItServiceException;

    List<Case> getCases() throws HouseItServiceException;

    List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException;

    List<Case> getCasesByType(String caseType) throws HouseItServiceException;

    List<Case> getCasesByFixDate() throws HouseItServiceException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException;

    void createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    void deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

}
