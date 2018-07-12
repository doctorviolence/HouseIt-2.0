package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;
import HouseIt.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private IBuildingDao buildingDao;

    @Autowired
    private IApartmentDao apartmentDao;

    @Autowired
    private ITenantDao tenantDao;

    @Autowired
    private ITaskDao taskDao;

    @Autowired
    private ITaskMessageDao taskMessageDao;

    public List<Building> getBuildings() {
        return buildingDao.getEntities(Building.class);
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        return apartmentDao.getApartmentsInBuilding(buildingId);
    }

    public List<Tenant> getTenantsInApartment(long apartmentId) {
        return tenantDao.getTenantsInFlat(apartmentId);
    }

    public Tenant findTenant(long id) throws MyEntityNotFoundException {
        Tenant t = tenantDao.findEntityById(Tenant.class, id);
        if (t == null) {
            throw new MyEntityNotFoundException(String.format("Tenant with ID %s not found.", id));
        }
        return t;
    }

    public void createTenant(Tenant tenant) {
        tenantDao.createEntity(tenant);
    }

    public void updateTenant(Tenant tenant) throws MyEntityNotFoundException {
        Tenant t = findTenant(tenant.getTenantId());
        if (t != null) {
            tenantDao.updateEntity(tenant);
        }
    }

    public void deleteTenant(long id) throws MyEntityNotFoundException {
        Tenant t = findTenant(id);
        if (t != null) {
            tenantDao.deleteEntity(Tenant.class, id);
        }
    }

    public List<Task> getTasks() {
        return taskDao.getEntities(Task.class);
    }

    public List<Task> findTasksByTenantId(long tenantId) {
        return taskDao.findTasksByTenantId(tenantId);
    }

    public List<Task> getTasksByType(String taskType) {
        return taskDao.getTasksByType(taskType);
    }

    public List<Task> getTasksByFixDate() {
        return taskDao.getTasksByFixDate();
    }

    public List<TaskMessage> getTaskMessagesByTask(long taskNo) {
        return taskMessageDao.getTaskMessagesByTask(taskNo);
    }

    public TaskMessage findMessage(long no) throws MyEntityNotFoundException {
        TaskMessage cm = taskMessageDao.findEntityById(TaskMessage.class, no);
        if (cm == null) {
            throw new MyEntityNotFoundException(String.format("Message no. %s not found.", no));
        }
        return cm;
    }

    public void createMessage(TaskMessage taskMessage) {
        taskMessageDao.createEntity(taskMessage);
    }

    public void deleteMessage(long no) throws MyEntityNotFoundException {
        TaskMessage cm = findMessage(no);
        if (cm != null) {
            taskMessageDao.deleteEntity(TaskMessage.class, no);
        }
    }

}