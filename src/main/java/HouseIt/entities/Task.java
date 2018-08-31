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

    @Column(name = "resolved")
    private String resolved;

    @Column(name = "task_date")
    private String taskDate;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id", nullable = false)
    private Building building;

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", nullable = false)
    private Tenant tenant;

    @JsonIgnore
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }


    public List<TaskMessage> getTaskMessages() {
        return taskMessages;
    }

    public void setTaskMessages(List<TaskMessage> taskMessages) {
        this.taskMessages = taskMessages;
    }

}
