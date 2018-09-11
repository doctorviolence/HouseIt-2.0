package HouseIt.dal;

import HouseIt.entities.Task;

import java.util.List;

public interface ITaskDao extends IBaseDao<Task> {

    List<Task> getTasksByDate();

    List<Task> getTodoTasksByDate();

    List<Task> getCompletedTasksByDate();

    List<Task> findTasksByTenantId(long tenantId);

    List<Task> findTodoTasksByTenantId(long tenantId);

    List<Task> findCompletedTasksByTenantId(long tenantId);

    List<Task> getTasksBySubject(String subject);

    Task createTask(Task task);

}
