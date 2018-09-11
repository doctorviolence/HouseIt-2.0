package HouseIt.service;

import HouseIt.entities.Task;
import HouseIt.exception.MyEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("taskService")
@Transactional
public interface ITaskService {

    List<Task> getTasks();

    List<Task> getTodoTasks();

    List<Task> getCompletedTasks();

    List<Task> getTasksBySubject(String subject);

    List<Task> findTasksByTenantId(long tenantId) throws MyEntityNotFoundException;

    List<Task> findTodoTasksByTenantId(long tenantId) throws MyEntityNotFoundException;

    List<Task> findCompletedTasksByTenantId(long tenantId) throws MyEntityNotFoundException;

    Task findTask(long taskNo) throws MyEntityNotFoundException;

    Task createTask(Task t);

    void updateTask(Task t) throws MyEntityNotFoundException;

    void deleteTask(long taskNo) throws MyEntityNotFoundException;

}