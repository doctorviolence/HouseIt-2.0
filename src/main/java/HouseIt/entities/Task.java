package HouseIt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_no", nullable = false)
    private long taskNo;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "resolved")
    private String resolved;

    @Column(name = "task_date")
    private String taskDate;

    @Column(name = "fix_date")
    private String fixDate;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "manager_id", nullable = false)
    private Manager manager;

    @JsonIgnore // temporary fix
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskMessage> taskMessages;

    public Task() {

    }

    public Task(long taskNo) {
        this.taskNo = taskNo;
    }

    public long getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(long taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getFixDate() {
        return fixDate;
    }

    public void setFixDate(String fixDate) {
        this.fixDate = fixDate;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<TaskMessage> getTaskMessages() {
        return taskMessages;
    }

    public void setTaskMessages(List<TaskMessage> taskMessages) {
        this.taskMessages = taskMessages;
    }

}
