package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.*;

import java.util.List;

public interface IAdminUserService {

    List<Building> getBuildings() throws HouseItServiceException;

    boolean createBuilding(Building building) throws HouseItServiceException;

    boolean updateBuilding(Building building) throws HouseItServiceException;

    boolean deleteBuilding(Building building) throws HouseItServiceException;

    List<Apartment> getApartmentsInBuilding(long buildingId) throws HouseItServiceException;

    boolean createApartment(Apartment apartment) throws HouseItServiceException;

    boolean updateApartment(Apartment apartment) throws HouseItServiceException;

    boolean deleteApartment(Apartment apartment) throws HouseItServiceException;

    List<Tenant> getTenantsInApartment(long apartmentId) throws HouseItServiceException;

    Tenant findTenant(Tenant tenant) throws HouseItServiceException;

    boolean createTenant(Tenant tenant) throws HouseItServiceException;

    boolean updateTenant(Tenant tenant) throws HouseItServiceException;

    boolean deleteTenant(Tenant tenant) throws HouseItServiceException;

    List<Case> getCases() throws HouseItServiceException;

    List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException;

    List<Case> getCasesByType(String caseType) throws HouseItServiceException;

    List<Case> getCasesByFixDate() throws HouseItServiceException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException;

    boolean createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    boolean deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    List<Manager> getManagers() throws HouseItServiceException;

    boolean createManager(Manager manager) throws HouseItServiceException;

    boolean updateManager(Manager manager) throws HouseItServiceException;

    boolean deleteManager(Manager manager) throws HouseItServiceException;

}
