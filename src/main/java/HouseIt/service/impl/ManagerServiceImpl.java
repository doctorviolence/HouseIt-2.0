package HouseIt.service.impl;

import HouseIt.dal.IManagerDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Manager;
import HouseIt.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("managerService")
public class ManagerServiceImpl implements IManagerService {

    private IManagerDao managerDao;

    @Autowired
    public ManagerServiceImpl(IManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    public List<Manager> getManagers() throws HouseItServiceException {
        try {
            return managerDao.getEntities(Manager.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get managers.", e);
        }
    }

    public Manager findManager(Manager manager) throws HouseItServiceException {
        try {
            long id = manager.getManagerId();
            return managerDao.findEntityById(Manager.class, id);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not find manager.", e);
        }
    }

    public boolean createManager(Manager manager) throws HouseItServiceException {
        boolean inserted = false;
        try {
            managerDao.createEntity(manager);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add manager.", e);
        }
        return inserted;
    }

    public boolean updateManager(Manager manager) throws HouseItServiceException {
        boolean updated = false;
        try {
            managerDao.updateEntity(manager);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update manager.", e);
        }
        return updated;
    }

    public boolean deleteManager(Manager manager) throws HouseItServiceException {
        try {
            Manager m = findManager(manager);
            if (m != null) {
                long id = m.getManagerId();
                managerDao.deleteEntity(Manager.class, id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete manager.", e);
        }
    }

}
