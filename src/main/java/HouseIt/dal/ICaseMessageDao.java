package HouseIt.dal;

import HouseIt.model.CaseMessage;

import java.util.Set;

public interface ICaseMessageDao extends IBaseDao<CaseMessage> {

    Set<CaseMessage> getCaseMessagesByCase(long caseNo);

}
