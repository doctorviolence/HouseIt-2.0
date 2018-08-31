package HouseIt.service.impl;

import HouseIt.dal.ITaskMessageDao;
import HouseIt.entities.TaskMessage;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITaskMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("taskMessageService")
@Transactional
public class TaskMessageServiceImpl implements ITaskMessageService {

    @Autowired
    private ITaskMessageDao taskMessageDao;

    public List<TaskMessage> getAllTaskMessages() {
        return taskMessageDao.getEntities(TaskMessage.class);
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

    public TaskMessage createMessage(TaskMessage taskMessage) {
        return taskMessageDao.createTaskMessage(taskMessage);
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