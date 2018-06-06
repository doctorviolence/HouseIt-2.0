package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Tenant;

import java.util.List;

public interface ITenantService {

    List<Tenant> getTenants() throws HouseItServiceException;

    Tenant findTenant(Tenant tenant) throws HouseItServiceException;

    boolean createTenant(Tenant tenant) throws HouseItServiceException;

    boolean updateTenant(Tenant tenant) throws HouseItServiceException;

    boolean deleteTenant(Tenant tenant) throws HouseItServiceException;

}
