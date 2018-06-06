package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Case;

import java.util.List;

public interface ICaseService {

    List<Case> getCases() throws HouseItServiceException;

    List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException;

    List<Case> getCasesByType(String caseType) throws HouseItServiceException;

    List<Case> getCasesByFixDate() throws HouseItServiceException;

    Case findCase(Case c) throws HouseItServiceException;

    boolean createCase(Case c) throws HouseItServiceException;

    boolean updateCase(Case c) throws HouseItServiceException;

    boolean deleteCase(Case c) throws HouseItServiceException;

}
