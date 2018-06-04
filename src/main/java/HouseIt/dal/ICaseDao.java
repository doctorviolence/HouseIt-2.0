package HouseIt.dal;

import HouseIt.model.Case;

import java.util.Set;

public interface ICaseDao extends IBaseDao<Case> {

    Set<Case> findCasesByTenantId(long tenantId);

    Set<Case> getCasesByType(String caseType);

    Set<Case> getCasesByFixDate();

}
