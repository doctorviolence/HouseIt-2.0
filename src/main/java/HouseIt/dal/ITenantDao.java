package HouseIt.dal;

import HouseIt.entities.Tenant;

import java.util.List;

public interface ITenantDao extends IBaseDao<Tenant> {

    List<Tenant> getTenantsInFlat(long apartmentId);

}
