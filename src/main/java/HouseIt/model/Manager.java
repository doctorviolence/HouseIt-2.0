package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "managers")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    private long managerId;
    private Set<Case> cases;

    public Manager() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "manager_id", nullable = false)
    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    public Set<Case> getCases() {
        return cases;
    }

    public void setCases(Set<Case> cases) {
        this.cases = cases;
    }

    @Override
    public int hashCode() {
        return (int) managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        } else if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        Manager m = (Manager) o;
        return (managerId == m.getManagerId());
    }

}
