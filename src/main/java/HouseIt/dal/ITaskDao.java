package HouseIt.dal;

import HouseIt.entities.Task;

import java.util.List;

public interface ITaskDao extends IBaseDao<Task> {

    List<Task> findTasksByTenantId(long tenantId);

    List<Task> getTasksByType(String taskType);

    List<Task> getTasksByFixDate();

    Task createTask(Task task);

}
