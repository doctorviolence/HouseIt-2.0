package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tenants")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;
    private long tenantId;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private Apartment apartment;
    private Set<Case> cases;

    public Tenant() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tenant_id", nullable = false)
    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone_no")
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id", nullable = false)
    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    public Set<Case> getCases() {
        return cases;
    }

    public void setCases(Set<Case> cases) {
        this.cases = cases;
    }

    @Override
    public int hashCode() {
        return (int) tenantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        } else if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        Tenant t = (Tenant) o;
        return (tenantId == t.getTenantId());
    }

}
