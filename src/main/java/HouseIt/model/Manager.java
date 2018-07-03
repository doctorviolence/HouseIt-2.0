package HouseIt.model;

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

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Case> cases;

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

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

}
