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

    @Column(name = "subject")
    private String subject;

    @Column(name = "resolved")
    private boolean resolved;

    @Column(name = "date_posted")
    private String datePosted;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Apartment getApartment() {
        return this.tenant.getApartment();
    }

    public void setApartment(Apartment apartment) {
        this.tenant.setApartment(apartment);
    }

    public Building getBuilding() {
        return this.tenant.getApartment().getBuilding();
    }

    public void setBuilding(Building building) {
        this.tenant.getApartment().setBuilding(building);
    }

    public List<TaskMessage> getTaskMessages() {
        return taskMessages;
    }

    public void setTaskMessages(List<TaskMessage> taskMessages) {
        this.taskMessages = taskMessages;
    }

}
