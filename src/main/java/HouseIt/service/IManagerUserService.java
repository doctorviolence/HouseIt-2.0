package HouseIt.service;

import HouseIt.exception.ServiceLayerException;
import HouseIt.model.*;

import java.util.List;

public interface IManagerUserService {

    List<Building> getBuildings() throws ServiceLayerException;

    List<Apartment> getApartmentsInBuilding(long buildingId) throws ServiceLayerException;

    List<Tenant> getTenantsInApartment(long apartmentId) throws ServiceLayerException;

    Tenant findTenant(Tenant tenant) throws ServiceLayerException;

    void createTenant(Tenant tenant) throws ServiceLayerException;

    void updateTenant(Tenant tenant) throws ServiceLayerException;

    void deleteTenant(Tenant tenant) throws ServiceLayerException;

    List<Case> getCases() throws ServiceLayerException;

    List<Case> findCasesByTenantId(long tenantId) throws ServiceLayerException;

    List<Case> getCasesByType(String caseType) throws ServiceLayerException;

    List<Case> getCasesByFixDate() throws ServiceLayerException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws ServiceLayerException;

    void createCaseMessage(CaseMessage caseMessage) throws ServiceLayerException;

    void deleteCaseMessage(CaseMessage caseMessage) throws ServiceLayerException;

}
