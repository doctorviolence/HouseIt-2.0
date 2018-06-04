package HouseIt.dal.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.model.Case;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

@Repository
public class CaseDao extends BaseDao<Case> implements ICaseDao {

    public Set<Case> findCasesByTenantId(long tenantId) {
        throw new NotImplementedException();
    }

    public Set<Case> getCasesByType(String caseType) {
        throw new NotImplementedException();
    }

    public Set<Case> getCasesByFixDate() {
        throw new NotImplementedException();
    }

}
