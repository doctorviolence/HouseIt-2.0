package HouseIt.dal.impl;

import HouseIt.dal.ITaskDao;
import HouseIt.entities.Task;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl extends BaseDaoImpl<Task> implements ITaskDao {

    @SuppressWarnings("unchecked")
    public List<Task> findTasksByTenantId(long tenantId) {
        return (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("tenant.tenantId", tenantId))
                .addOrder(Order.desc("taskDate"))
                .list();
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
        return (List<Task>) getCurrentSession().createCriteria(Task.class)
                .addOrder(Order.desc("taskDate"))
                .list();
    }

    public Task createTask(Task task) {
        getCurrentSession().saveOrUpdate(task);
        return task;
    }

}
