package HouseIt.dal.impl;

import HouseIt.dal.ICaseDao;
import HouseIt.model.Case;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaseDaoImpl extends BaseDaoImpl<Case> implements ICaseDao {

    @SuppressWarnings("unchecked")
    public List<Case> findCasesByTenantId(long tenantId) {
        return (List<Case>) getCurrentSession().createCriteria(Case.class)
                .add(Restrictions.eq("tenant.tenantId", tenantId))
                .addOrder(Order.asc("caseDate"))
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Case> getCasesByType(String caseType) {
        return (List<Case>) getCurrentSession().createCriteria(Case.class)
                .add(Restrictions.eq("caseType", caseType))
                .addOrder(Order.asc("caseDate"))
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Case> getCasesByFixDate() {
        return (List<Case>) getCurrentSession().createCriteria(Case.class)
                .addOrder(Order.asc("fixDate"))
                .list();
    }

}
