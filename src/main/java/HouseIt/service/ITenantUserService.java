package HouseIt.service;

import HouseIt.model.Case;
import HouseIt.model.CaseMessage;

import java.util.List;

public interface ITenantUserService {

    List<Case> findCasesByTenantId(long tenantId);

    Case findCase(long caseNo);

    void createCase(Case c);

    void updateCase(Case c);

    void deleteCase(long caseNo);

    List<CaseMessage> getCaseMessagesByCase(long caseNo);

    CaseMessage findMessage(long messageNo);

    void createMessage(CaseMessage caseMessage);

    void updateMessage(CaseMessage caseMessage);

    void deleteMessage(long messageNo);

}