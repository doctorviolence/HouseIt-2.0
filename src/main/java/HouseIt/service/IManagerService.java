package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.*;

import java.util.List;

public interface IManagerService {

    List<Building> getBuildings();

    List<Apartment> getApartmentsInBuilding(long buildingId);

    List<Tenant> getTenantsInApartment(long apartmentId);

    Tenant findTenant(long tenantId) throws MyEntityNotFoundException;

    void createTenant(Tenant tenant);

    void updateTenant(Tenant tenant) throws MyEntityNotFoundException;

    void deleteTenant(long tenantId) throws MyEntityNotFoundException;

    List<Task> getTasks();

    List<Task> findTasksByTenantId(long tenantId);

    List<Task> getTasksByType(String taskType);

    List<Task> getTasksByFixDate();

    List<TaskMessage> getTaskMessagesByTask(long taskNo);

    TaskMessage findMessage(long messageNo) throws MyEntityNotFoundException;

    void createMessage(TaskMessage taskMessage);

    void deleteMessage(long messageNo) throws MyEntityNotFoundException;

}
