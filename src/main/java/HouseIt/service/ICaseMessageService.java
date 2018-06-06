package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.CaseMessage;

import java.util.List;

public interface ICaseMessageService {

    List<CaseMessage> getCaseMessages() throws HouseItServiceException;

    List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException;

    CaseMessage findCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    boolean createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    boolean updateCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

    boolean deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException;

}
