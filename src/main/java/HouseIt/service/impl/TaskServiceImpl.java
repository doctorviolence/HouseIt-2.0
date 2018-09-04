package HouseIt.service.impl;

import HouseIt.dal.ITaskDao;
import HouseIt.entities.Task;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("taskService")
@Transactional
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private ITaskDao taskDao;

    public List<Task> getTasks() {
        return taskDao.getTasksByDate();
    }

    public List<Task> getTasksByType(String taskType) {
        return taskDao.getTasksByType(taskType);
    }

    public List<Task> getTasksByFixDate() {
        return taskDao.getTasksByDate();
    }

    public List<Task> findTasksByTenantId(long tenantId) throws MyEntityNotFoundException {
        List<Task> tasks = taskDao.findTasksByTenantId(tenantId);
        if (tasks.isEmpty()) {
            throw new MyEntityNotFoundException(String.format("No tasks found for tenant id (%s).", tenantId));
        }
        return tasks;
    }

    public Task findTask(long taskNo) throws MyEntityNotFoundException {
        Task t = taskDao.findEntityById(Task.class, taskNo);
        if (t == null) {
            throw new MyEntityNotFoundException(String.format("Task no. %s not found.", taskNo));
        }
        return t;
    }

    public Task createTask(Task task) {
        return taskDao.createTask(task);
    }

    public void updateTask(Task task) throws MyEntityNotFoundException {
        Task t = findTask(task.getTaskNo());
        if (t != null) {
            taskDao.updateEntity(task);
        }
    }

    public void deleteTask(long taskNo) throws MyEntityNotFoundException {
        Task t = findTask(taskNo);
        if (t != null) {
            taskDao.deleteEntity(Task.class, taskNo);
        }
    }

}