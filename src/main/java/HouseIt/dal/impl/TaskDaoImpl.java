package HouseIt.dal.impl;

import HouseIt.dal.ITaskDao;
import HouseIt.entities.Task;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TaskDaoImpl extends BaseDaoImpl<Task> implements ITaskDao {

    @SuppressWarnings("unchecked")
    public List<Task> findTasksByTenantId(long tenantId) {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("tenant.tenantId", tenantId))
                .addOrder(Order.desc("taskDate"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksByType(String taskType) {
        return (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("taskType", taskType))
                .addOrder(Order.desc("taskDate"))
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksByDate() {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .addOrder(Order.desc("taskDate"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());

    }

    public Task createTask(Task task) {
        getCurrentSession().saveOrUpdate(task);
        return task;
    }

}
