package HouseIt.service.impl;

import HouseIt.dal.ICaseMessageDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.CaseMessage;
import HouseIt.service.ICaseMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("caseMessageService")
public class CaseMessageServiceImpl implements ICaseMessageService {

    private ICaseMessageDao caseMessageDao;

    @Autowired
    public CaseMessageServiceImpl(ICaseMessageDao caseMessageDao) {
        this.caseMessageDao = caseMessageDao;
    }

    public List<CaseMessage> getCaseMessages() throws HouseItServiceException {
        try {
            return caseMessageDao.getEntities(CaseMessage.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get case messages.", e);
        }
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException {
        try {
            return caseMessageDao.getCaseMessagesByCase(caseNo);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get case messages.", e);
        }
    }

    public CaseMessage findCaseMessage(CaseMessage caseMessage) throws HouseItServiceException {
        try {
            long no = caseMessage.getMessageNo();
            return caseMessageDao.findEntityById(CaseMessage.class, no);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not find case messages.", e);
        }
    }

    public boolean createCaseMessage(CaseMessage caseMessage) throws HouseItServiceException {
        boolean inserted = false;
        try {
            caseMessageDao.createEntity(caseMessage);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add case message.", e);
        }
        return inserted;
    }

    public boolean updateCaseMessage(CaseMessage caseMessage) throws HouseItServiceException {
        boolean updated = false;
        try {
            caseMessageDao.updateEntity(caseMessage);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update case message.", e);
        }
        return updated;
    }

    public boolean deleteCaseMessage(CaseMessage caseMessage) throws HouseItServiceException {
        try {
            CaseMessage c = findCaseMessage(caseMessage);
            if (c != null) {
                long no = c.getMessageNo();
                caseMessageDao.deleteEntity(CaseMessage.class, no);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete case message.", e);
        }
    }

}
