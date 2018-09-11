package HouseIt.dal.impl;

import HouseIt.dal.ITaskMessageDao;
import HouseIt.entities.TaskMessage;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaskMessageDaoImpl extends BaseDaoImpl<TaskMessage> implements ITaskMessageDao {

    @SuppressWarnings("unchecked")
    public List<TaskMessage> getTaskMessagesByTask(long taskNo) {
        List<TaskMessage> messages = (List<TaskMessage>) getCurrentSession().createCriteria(TaskMessage.class)
                .add(Restrictions.eq("task.taskNo", taskNo))
                .addOrder(Order.asc("timePosted"))
                .list();

        return messages.stream().distinct().collect(Collectors.toList());

    }

    public TaskMessage createTaskMessage(TaskMessage taskMessage) {
        getCurrentSession().saveOrUpdate(taskMessage);
        return taskMessage;
    }

}
