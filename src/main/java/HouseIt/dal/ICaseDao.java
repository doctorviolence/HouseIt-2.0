package HouseIt.dal;

import HouseIt.model.Case;

import java.util.List;

public interface ICaseDao extends IBaseDao<Case> {

    List<Case> findCasesByTenantId(long tenantId);

    List<Case> getCasesByType(String caseType);

    List<Case> getCasesByFixDate();

}
