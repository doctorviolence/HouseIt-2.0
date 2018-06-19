package HouseIt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cases")
public class Case implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "case_no", nullable = false)
    private long caseNo;

    @Column(name = "case_type")
    private String caseType;

    @Column(name = "case_status")
    private String caseStatus;

    @Column(name = "resolved")
    private String resolved;

    @Column(name = "case_date")
    private String caseDate;

    @Column(name = "fix_date")
    private String fixDate;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "manager_id", nullable = false)
    private Manager manager;

    @OneToMany(mappedBy = "c", fetch = FetchType.LAZY)
    private List<CaseMessage> caseMessages;

    public Case() {

    }

    public Case(long caseNo) {
        this.caseNo = caseNo;
    }

    public long getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(long caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(String caseDate) {
        this.caseDate = caseDate;
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

    public List<CaseMessage> getCaseMessages() {
        return caseMessages;
    }

    public void setCaseMessages(List<CaseMessage> caseMessages) {
        this.caseMessages = caseMessages;
    }

}
