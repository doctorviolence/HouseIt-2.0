package HouseIt.service.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.dal.ICaseMessageDao;
import HouseIt.exception.ServiceLayerException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tenantUserService")
public class TenantServiceImpl implements ITenantService {

    private ICaseDao caseDao;
    private ICaseMessageDao caseMessageDao;

    @Autowired
    public TenantServiceImpl(ICaseDao caseDao, ICaseMessageDao caseMessageDao) {
        this.caseDao = caseDao;
        this.caseMessageDao = caseMessageDao;
    }

    public List<Case> findCasesByTenantId(long tenantId) {
        List<Case> cases = caseDao.findCasesByTenantId(tenantId);

        if (cases != null) {
            return caseDao.findCasesByTenantId(tenantId);
        } else {
            throw new ServiceLayerException();
        }
    }

    public Case findCase(long caseNo) {
        return caseDao.findEntityById(Case.class, caseNo);
    }

    public void createCase(Case c) {
        if (c != null) {
            caseDao.createEntity(c);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateCase(Case c) {
        if (c != null) {
            caseDao.updateEntity(c);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteCase(long caseNo) {
        Case c = findCase(caseNo);

        if (c != null) {
            caseDao.deleteEntity(Case.class, caseNo);
        } else {
            throw new ServiceLayerException();
        }
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) {
        List<CaseMessage> caseMessages = caseMessageDao.getCaseMessagesByCase(caseNo);

        if (caseMessages != null) {
            return caseMessages;
        } else {
            throw new ServiceLayerException();
        }
    }

    public CaseMessage findMessage(long messageNo) {
        return caseMessageDao.findEntityById(CaseMessage.class, messageNo);
    }

    public void createMessage(CaseMessage caseMessage) {
        if (caseMessage != null) {
            caseMessageDao.createEntity(caseMessage);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void updateMessage(CaseMessage caseMessage) {
        if (caseMessage != null) {
            caseMessageDao.updateEntity(caseMessage);
        } else {
            throw new ServiceLayerException();
        }
    }

    public void deleteMessage(long messageNo) {
        CaseMessage cm = findMessage(messageNo);

        if (cm != null) {
            caseMessageDao.deleteEntity(CaseMessage.class, messageNo);
        } else {
            throw new ServiceLayerException();
        }
    }

}