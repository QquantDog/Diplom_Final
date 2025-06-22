package com.senla.dao.cab;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.cab.Cab;
import com.senla.model.cab.Cab_;
import com.senla.model.vehicle.Vehicle;
import com.senla.model.vehicle.Vehicle_;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CabDao extends AbstractLongDao<Cab> implements ICabDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Cab.class;
    }

    @Override
    public List<Cab> getAllCabs() {
        TypedQuery<Cab> q = em.createQuery("SELECT c FROM Cab c join fetch c.vehicle v join fetch v.brand join fetch c.company", Cab.class);
        return q.getResultList();
    }

    @Override
    public List<Cab> getRegisteredCabs(Long driverId) {
        TypedQuery<Cab> q = em.createQuery("""
                          select cab from Cab cab
                          join cab.company c
                          join c.registrationEntries re
                          join re.driver d
                          where d.id = :driverId
                          """, Cab.class);
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }

    @Override
    public List<Cab> getPossibleCabs(Long driverId) {
        TypedQuery<Cab> q = em.createQuery("""
                          select cab from Cab cab
                          join cab.company c
                          join c.registrationEntries re
                          join re.driver d
                          where d.id = :driverId
                              and cab.isOnShift = false
                          """, Cab.class);
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }

    @Override
    public List<Cab> findWithFilterAndPagination(Specification<Cab> nativeSpec, PaginationDetails paginationDetails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cab> cq = cb.createQuery(Cab.class);
        Root<Cab> root = cq.from(Cab.class);

        root.fetch(Cab_.company);
        Fetch<Cab, Vehicle> v = root.fetch(Cab_.vehicle);
        v.fetch(Vehicle_.brand);

        root.get(Cab_.company);
        cq.select(root);
        cq.where(cb.and(nativeSpec.toPredicate(root, cq, cb)));
        TypedQuery<Cab> query = em.createQuery(cq);

        query.setFirstResult(paginationDetails.getOffset());
        query.setMaxResults(paginationDetails.getLimit());

        return query.getResultList();
    }
}
