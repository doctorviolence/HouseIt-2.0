package HouseIt.service;

import HouseIt.entities.Tenant;
import HouseIt.exception.MyEntityNotFoundException;

import java.util.List;

public interface ITenantService {

    List<Tenant> getTenants();

    List<Tenant> getTenantsInApartment(long apartmentId);

    Tenant findTenant(long tenantId) throws MyEntityNotFoundException;

    Tenant createTenant(Tenant tenant);

    void updateTenant(Tenant tenant) throws MyEntityNotFoundException;

    void deleteTenant(long tenantId) throws MyEntityNotFoundException;

}