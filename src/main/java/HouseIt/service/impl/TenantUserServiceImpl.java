package HouseIt.service.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.dal.ICaseMessageDao;
import HouseIt.exception.ServiceLayerException;
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

    public List<Case> findCasesByTenantId(long tenantId) throws ServiceLayerException {
        try {
            return caseDao.findCasesByTenantId(tenantId);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get cases.", e);
        }
    }

    public void createCase(Case c) throws ServiceLayerException {
        try {
            caseDao.createEntity(c);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add case.", e);
        }
    }

    public void updateCase(Case c) throws ServiceLayerException {
        try {
            caseDao.updateEntity(c);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update case.", e);
        }
    }

    public void deleteCase(Case c) throws ServiceLayerException {
        try {
            long no = c.getCaseNo();
            caseDao.deleteEntity(Case.class, no);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete case.", e);
        }
    }

    public List<CaseMessage> getCaseMessagesByCase(long caseNo) throws ServiceLayerException {
        try {
            return caseMessageDao.getCaseMessagesByCase(caseNo);
        } catch (Exception e) {
            throw new ServiceLayerException("Could not get case messages.", e);
        }
    }

    public void createCaseMessage(CaseMessage caseMessage) throws ServiceLayerException {
        try {
            caseMessageDao.createEntity(caseMessage);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to add case message.", e);
        }
    }

    public void updateCaseMessage(CaseMessage caseMessage) throws ServiceLayerException {
        try {
            caseMessageDao.updateEntity(caseMessage);
        } catch (Exception e) {
            throw new ServiceLayerException("Unable to update case message.", e);
        }
    }

    public void deleteCaseMessage(CaseMessage caseMessage) throws ServiceLayerException {
        try {
            long no = caseMessage.getMessageNo();
            caseMessageDao.deleteEntity(CaseMessage.class, no);
        } catch (Exception e) {
            throw new ServiceLayerException("Failed to delete case message.", e);
        }
    }

}
