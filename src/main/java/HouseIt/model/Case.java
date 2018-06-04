package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "cases")
public class Case implements Serializable {

    private static final long serialVersionUID = 1L;
    private long caseNo;
    private String caseType;
    private String caseStatus;
    private String resolved;
    private String caseDate;
    private String fixDate;
    private Tenant tenant;
    private Manager manager;
    private Set<CaseMessage> caseMessages;

    public Case() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "case_no", nullable = false)
    public long getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(long caseNo) {
        this.caseNo = caseNo;
    }

    @Column(name = "case_type")
    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    @Column(name = "case_status")
    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    @Column(name = "resolved")
    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    @Column(name = "case_date")
    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
    }

    @Column(name = "fix_date")
    public String getFixDate() {
        return fixDate;
    }

    public void setFixDate(String fixDate) {
        this.fixDate = fixDate;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", nullable = false)
    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "manager_id", nullable = false)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @OneToMany(mappedBy = "case", fetch = FetchType.LAZY)
    public Set<CaseMessage> getCaseMessages() {
        return caseMessages;
    }

    public void setCaseMessages(Set<CaseMessage> caseMessages) {
        this.caseMessages = caseMessages;
    }

    @Override
    public int hashCode() {
        return (int) caseNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o && this.getClass() == o.getClass()) {
            return true;
        } else if (o == null && this.getClass() != o.getClass()) {
            return false;
        }

        Case c = (Case) o;
        return (caseNo == c.getCaseNo());
    }

}
