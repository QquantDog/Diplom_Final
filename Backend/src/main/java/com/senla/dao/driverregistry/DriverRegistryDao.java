package com.senla.dao.driverregistry;


import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.driverregistry.DriverRegistry;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DriverRegistryDao extends AbstractLongDao<DriverRegistry> implements IDriverRegistryDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = DriverRegistry.class;
    }

    @Override
    public List<DriverRegistry> getAll() {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select dr from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          join fetch dr.driver d
                          """, DriverRegistry.class);
        return q.getResultList();
    }

    @Override
    public List<DriverRegistry> getByDriverId(Long driverId) {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select dr from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          join dr.driver d
                          where d.id=:driverId
                          """, DriverRegistry.class);
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }

    @Override
    public Optional<DriverRegistry> getEntry(Long driverId, Long companyId) {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select dr from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          join fetch dr.driver d
                          where d.id=:driverId
                              and t.id=:companyId
                          """, DriverRegistry.class);
        q.setParameter("driverId", driverId);
        q.setParameter("companyId", companyId);
        return q.getResultStream().findFirst();
    }

    @Override
    public List<DriverRegistry> findAllWithPagination(PaginationDetails paginationDetails) {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select dr from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          join fetch dr.driver d
                          """, DriverRegistry.class);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        return q.getResultList();
    }
}
