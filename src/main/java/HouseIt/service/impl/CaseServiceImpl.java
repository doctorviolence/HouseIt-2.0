package HouseIt.service.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Case;
import HouseIt.service.ICaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("caseService")
public class CaseServiceImpl implements ICaseService {

    private ICaseDao caseDao;

    @Autowired
    public CaseServiceImpl(ICaseDao caseDao) {
        this.caseDao = caseDao;
    }

    public List<Case> getCases() throws HouseItServiceException {
        try {
            return caseDao.getEntities(Case.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Case> findCasesByTenantId(long tenantId) throws HouseItServiceException {
        try {
            return caseDao.findCasesByTenantId(tenantId);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Case> getCasesByType(String caseType) throws HouseItServiceException {
        try {
            return caseDao.getCasesByType(caseType);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public List<Case> getCasesByFixDate() throws HouseItServiceException {
        try {
            return caseDao.getCasesByFixDate();
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get cases.", e);
        }
    }

    public Case findCase(Case c) throws HouseItServiceException {
        try {
            long no = c.getCaseNo();
            return caseDao.findEntityById(Case.class, no);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not find case.", e);
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
        try {
            Case caze = findCase(c);
            if (caze != null) {
                long no = caze.getCaseNo();
                caseDao.deleteEntity(Case.class, no);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete case.", e);
        }
    }

}
