package HouseIt.service.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.dal.ICaseMessageDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Case;
import HouseIt.model.CaseMessage;
import HouseIt.service.ITenantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tenantUserService")
public class TenantUserServiceImpl implements ITenantUserService {

    private ICaseDao caseDao;
    private ICaseMessageDao caseMessageDao;

    @Autowired
    public TenantUserServiceImpl(ICaseDao caseDao, ICaseMessageDao caseMessageDao) {
        this.caseDao = caseDao;
        this.caseMessageDao = caseMessageDao;
    }

    public List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException {
        try {
            return caseDao.findCasesByTenantId(tenantId);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public boolean createCase(Case c) throws HouseItServiceException {
        boolean inserted = false;
        try {
            caseDao.createEntity(c);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add case.", e);
        }
        return inserted;
    }

    public boolean updateCase(Case c) throws HouseItServiceException {
        boolean updated = false;
        try {
            caseDao.updateEntity(c);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update case.", e);
        }
        return updated;
    }

    public boolean deleteCase(Case c) throws HouseItServiceException {
        boolean deleted = false;
        try {
            long no = c.getCaseNo();
            caseDao.deleteEntity(Case.class, no);
            deleted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete case.", e);
        }
        return deleted;
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) throws HouseItServiceException {
        try {
            return caseMessageDao.getCaseMessagesByCase(caseNo);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get case messages.", e);
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
        boolean deleted = false;
        try {
            long no = caseMessage.getMessageNo();
            caseMessageDao.deleteEntity(CaseMessage.class, no);
            deleted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete case message.", e);
        }
        return deleted;
    }

}
