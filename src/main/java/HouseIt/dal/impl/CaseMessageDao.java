package HouseIt.dal.impl;

import HouseIt.dal.ICaseMessageDao;
import HouseIt.model.CaseMessage;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

@Repository
public class CaseMessageDao extends BaseDao<CaseMessage> implements ICaseMessageDao {

    public Set<CaseMessage> getCaseMessagesByCase(long caseNo) {
        throw new NotImplementedException();
    }

}
