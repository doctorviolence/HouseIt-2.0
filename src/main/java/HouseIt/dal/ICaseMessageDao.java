package HouseIt.dal;

import HouseIt.model.CaseMessage;

import java.util.List;

public interface ICaseMessageDao extends IBaseDao<CaseMessage> {

    List<CaseMessage> getCaseMessagesByCase(long caseNo);

}
