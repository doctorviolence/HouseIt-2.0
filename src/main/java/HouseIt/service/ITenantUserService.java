package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;

import java.util.List;

public interface ITenantUserService {

    List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException;

    void createCase(Case c) throws HouseItServiceException;

    void updateCase(Case c) throws HouseItServiceException;

    void deleteCase(Case c) throws HouseItServiceException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException;

    void createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    void updateCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    void deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

}
