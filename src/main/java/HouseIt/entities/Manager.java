package HouseIt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "managers")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "manager_id", nullable = false)
    private long managerId;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Task> tasks;

    public Manager() {

    }

    public Manager(long managerId) {
        this.managerId = managerId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
