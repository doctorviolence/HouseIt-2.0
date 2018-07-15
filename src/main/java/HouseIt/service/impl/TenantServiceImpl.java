package HouseIt.service.impl;

import HouseIt.dal.ITaskDao;
import HouseIt.dal.ITaskMessageDao;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.Task;
import HouseIt.entities.TaskMessage;
import HouseIt.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("tenantService")
@Transactional
public class TenantServiceImpl implements ITenantService {

    @Autowired
    private ITaskDao taskDao;

    @Autowired
    private ITaskMessageDao taskMessageDao;

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

    public void createTask(Task task) {
        taskDao.createEntity(task);
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

    public List<TaskMessage> getTaskMessagesByTask(long taskNo) throws MyEntityNotFoundException {
        List<TaskMessage> taskMessages = taskMessageDao.getTaskMessagesByTask(taskNo);
        if (taskMessages == null) {
            throw new MyEntityNotFoundException(String.format("Couldn't find messages for task no. %s.", taskNo));
        }
        return taskMessages;
    }

    public TaskMessage findMessage(long messageNo) throws MyEntityNotFoundException {
        TaskMessage m = taskMessageDao.findEntityById(TaskMessage.class, messageNo);
        if (m == null) {
            throw new MyEntityNotFoundException(String.format("Message no. %s not found.", messageNo));
        }
        return m;
    }

    public void createMessage(TaskMessage taskMessage) {
        taskMessageDao.createEntity(taskMessage);
    }

    public void updateMessage(TaskMessage taskMessage) throws MyEntityNotFoundException {
        TaskMessage m = findMessage(taskMessage.getMessageNo());
        if (m != null) {
            taskMessageDao.updateEntity(taskMessage);
        }
    }

    public void deleteMessage(long messageNo) throws MyEntityNotFoundException {
        TaskMessage m = findMessage(messageNo);
        if (m != null) {
            taskMessageDao.deleteEntity(TaskMessage.class, messageNo);
        }
    }

}