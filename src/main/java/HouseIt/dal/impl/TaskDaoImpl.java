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
    public List<Task> getTasksByDate() {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .addOrder(Order.desc("datePosted"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTodoTasksByDate() {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("resolved", false))
                .addOrder(Order.desc("datePosted"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> getCompletedTasksByDate() {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("resolved", true))
                .addOrder(Order.desc("datePosted"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> findTasksByTenantId(long tenantId) {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("tenant.tenantId", tenantId))
                .addOrder(Order.desc("datePosted"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> findTodoTasksByTenantId(long tenantId) {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("tenant.tenantId", tenantId))
                .add(Restrictions.eq("resolved", false))
                .addOrder(Order.desc("datePosted"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> findCompletedTasksByTenantId(long tenantId) {
        List<Task> tasks = (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("tenant.tenantId", tenantId))
                .add(Restrictions.eq("resolved", true))
                .addOrder(Order.desc("datePosted"))
                .list();

        return tasks.stream().distinct().collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Task> getTasksBySubject(String subject) {
        return (List<Task>) getCurrentSession().createCriteria(Task.class)
                .add(Restrictions.eq("subject", subject))
                .addOrder(Order.desc("datePosted"))
                .list();
    }

    public Task createTask(Task task) {
        getCurrentSession().saveOrUpdate(task);
        return task;
    }

}
