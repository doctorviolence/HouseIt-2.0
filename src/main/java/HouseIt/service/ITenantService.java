package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;

import java.util.List;

public interface ITenantService {

    List<Case> findCasesByTenantId(long tenantId) throws MyEntityNotFoundException;

    Case findCase(long caseNo) throws MyEntityNotFoundException;

    void createCase(Case c);

    void updateCase(Case c);

    void deleteCase(long caseNo) throws MyEntityNotFoundException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws MyEntityNotFoundException;

    CaseMessage findMessage(long messageNo) throws MyEntityNotFoundException;

    void createMessage(CaseMessage caseMessage);

    void updateMessage(CaseMessage caseMessage);

    void deleteMessage(long messageNo) throws MyEntityNotFoundException;

}