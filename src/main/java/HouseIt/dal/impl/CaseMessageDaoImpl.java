package HouseIt.dal.impl;

import HouseIt.dal.ICaseMessageDao;
import HouseIt.model.CaseMessage;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaseMessageDaoImpl extends BaseDaoImpl<CaseMessage> implements ICaseMessageDao {

    @SuppressWarnings("unchecked")
    public List<CaseMessage> getCaseMessagesByCase(long caseNo) {
        return (List<CaseMessage>) getCurrentSession().createCriteria(CaseMessage.class)
                .add(Restrictions.eq("c.caseNo", caseNo))
                .list();
    }

}
