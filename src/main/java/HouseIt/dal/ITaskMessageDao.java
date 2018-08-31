package HouseIt.dal;

import HouseIt.entities.TaskMessage;

import java.util.List;

public interface ITaskMessageDao extends IBaseDao<TaskMessage> {

    List<TaskMessage> getTaskMessagesByTask(long caseNo);

    TaskMessage createTaskMessage(TaskMessage taskMessage);

}
