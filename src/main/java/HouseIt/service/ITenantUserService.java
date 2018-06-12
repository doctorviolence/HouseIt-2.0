package HouseIt.service;

import HouseIt.exception.ServiceLayerException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;

import java.util.List;

public interface ITenantUserService {

    List<Case> findCasesByTenantId(long tenantId) throws ServiceLayerException;

    void createCase(Case c) throws ServiceLayerException;

    void updateCase(Case c) throws ServiceLayerException;

    void deleteCase(Case c) throws ServiceLayerException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws ServiceLayerException;

    void createCaseMessage(CaseMessage caseMessage) throws ServiceLayerException;

    void updateCaseMessage(CaseMessage caseMessage) throws ServiceLayerException;

    void deleteCaseMessage(CaseMessage caseMessage) throws ServiceLayerException;

}
