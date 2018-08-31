package HouseIt.dal.impl;

import HouseIt.dal.ITaskMessageDao;
import HouseIt.entities.TaskMessage;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskMessageDaoImpl extends BaseDaoImpl<TaskMessage> implements ITaskMessageDao {

    @SuppressWarnings("unchecked")
    public List<TaskMessage> getTaskMessagesByTask(long taskNo) {
        return (List<TaskMessage>) getCurrentSession().createCriteria(TaskMessage.class)
                .add(Restrictions.eq("task.taskNo", taskNo))
                .list();
    }

    public TaskMessage createTaskMessage(TaskMessage taskMessage) {
        getCurrentSession().saveOrUpdate(taskMessage);
        return taskMessage;
    }

}
