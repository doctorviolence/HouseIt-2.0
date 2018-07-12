package HouseIt.service;

import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.model.Task;
import HouseIt.model.TaskMessage;

import java.util.List;

public interface ITenantService {

    List<Task> findTasksByTenantId(long tenantId) throws MyEntityNotFoundException;

    Task findTask(long caseNo) throws MyEntityNotFoundException;

    void createTask(Task c);

    void updateTask(Task c) throws MyEntityNotFoundException;

    void deleteTask(long caseNo) throws MyEntityNotFoundException;

    List<TaskMessage> getTaskMessagesByTask(long caseNo) throws MyEntityNotFoundException;

    TaskMessage findMessage(long messageNo) throws MyEntityNotFoundException;

    void createMessage(TaskMessage taskMessage);

    void updateMessage(TaskMessage taskMessage) throws MyEntityNotFoundException;

    void deleteMessage(long messageNo) throws MyEntityNotFoundException;

}