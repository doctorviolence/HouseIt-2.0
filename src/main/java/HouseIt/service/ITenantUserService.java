package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;

import java.util.List;

public interface ITenantUserService {

    List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException;

    boolean createCase(Case c) throws HouseItServiceException;

    boolean updateCase(Case c) throws HouseItServiceException;

    boolean deleteCase(Case c) throws HouseItServiceException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException;

    boolean createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    boolean updateCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    boolean deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

}
