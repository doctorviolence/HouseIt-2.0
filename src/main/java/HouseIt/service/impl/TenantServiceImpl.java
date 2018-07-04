package HouseIt.service.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.dal.ICaseMessageDao;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("tenantService")
@Transactional
public class TenantServiceImpl implements ITenantService {

    @Autowired
    private ICaseDao caseDao;

    @Autowired
    private ICaseMessageDao caseMessageDao;

    public List<Case> findCasesByTenantId(long tenantId) throws MyEntityNotFoundException {
        List<Case> cases = caseDao.findCasesByTenantId(tenantId);
        if (cases == null) {
            throw new MyEntityNotFoundException(String.format("Cases by tenant id: %s not found.", tenantId));
        }
        return cases;
    }

    public Case findCase(long caseNo) throws MyEntityNotFoundException {
        Case c = caseDao.findEntityById(Case.class, caseNo);
        if (c == null) {
            throw new MyEntityNotFoundException(String.format("Case no. %s not found.", caseNo));
        }
        return c;
    }

    public void createCase(Case c) {
        caseDao.createEntity(c);
    }

    public void updateCase(Case c) {
        caseDao.updateEntity(c);
    }

    public void deleteCase(long caseNo) throws MyEntityNotFoundException {
        Case c = findCase(caseNo);
        if (c != null) {
            caseDao.deleteEntity(Case.class, caseNo);
        }
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) throws MyEntityNotFoundException {
        List<CaseMessage> caseMessages = caseMessageDao.getCaseMessagesByCase(caseNo);
        if (caseMessages == null) {
            throw new MyEntityNotFoundException(String.format("Couldn't find messages for case no. %s.", caseNo));
        }
        return caseMessages;
    }

    public CaseMessage findMessage(long messageNo) throws MyEntityNotFoundException {
        CaseMessage cm = caseMessageDao.findEntityById(CaseMessage.class, messageNo);
        if (cm == null) {
            throw new MyEntityNotFoundException(String.format("Message no. %s not found.", messageNo));
        }
        return cm;
    }

    public void createMessage(CaseMessage caseMessage) {
        caseMessageDao.createEntity(caseMessage);
    }

    public void updateMessage(CaseMessage caseMessage) {
        caseMessageDao.updateEntity(caseMessage);
    }

    public void deleteMessage(long messageNo) throws MyEntityNotFoundException {
        CaseMessage cm = findMessage(messageNo);
        if (cm != null) {
            caseMessageDao.deleteEntity(CaseMessage.class, messageNo);
        }
    }

}