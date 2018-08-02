package HouseIt.service;

import HouseIt.entities.TaskMessage;
import HouseIt.exception.MyEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("taskMessageService")
@Transactional
public interface ITaskMessageService {

    List<TaskMessage> getTaskMessagesByTask(long caseNo) throws MyEntityNotFoundException;

    TaskMessage findMessage(long messageNo) throws MyEntityNotFoundException;

    void createMessage(TaskMessage taskMessage);

    void updateMessage(TaskMessage taskMessage) throws MyEntityNotFoundException;

    void deleteMessage(long messageNo) throws MyEntityNotFoundException;

}
