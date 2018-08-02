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

    List<Task> getTasksByType(String taskType);

    List<Task> getTasksByFixDate();

    List<Task> findTasksByTenantId(long tenantId) throws MyEntityNotFoundException;

    Task findTask(long caseNo) throws MyEntityNotFoundException;

    void createTask(Task c);

    void updateTask(Task c) throws MyEntityNotFoundException;

    void deleteTask(long caseNo) throws MyEntityNotFoundException;

}