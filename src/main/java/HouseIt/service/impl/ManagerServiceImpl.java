package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;
import HouseIt.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private IManagerDao managerDao;

    public List<Manager> getManagers() {
        return managerDao.getEntities(Manager.class);
    }

    public Manager findManager(long id) throws MyEntityNotFoundException {
        Manager m = managerDao.findEntityById(Manager.class, id);
        if (m == null) {
            throw new MyEntityNotFoundException(String.format("Manager with ID %s not found.", id));
        }
        return m;
    }

    public void createManager(Manager manager) {
        managerDao.createEntity(manager);
    }

    public void updateManager(Manager manager) throws MyEntityNotFoundException {
        Manager m = findManager(manager.getManagerId());
        if (m != null) {
            managerDao.updateEntity(manager);
        }
    }

    public void deleteManager(long id) throws MyEntityNotFoundException {
        Manager m = findManager(id);
        if (m != null) {
            managerDao.deleteEntity(Manager.class, id);
        }
    }

}