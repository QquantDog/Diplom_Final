package com.senla.repository.cab;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.cab.Cab;
import com.senla.model.cab.Cab_;
import com.senla.model.vehicle.Vehicle;
import com.senla.model.vehicle.Vehicle_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CabRepositoryImpl implements CabRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Cab> findWithFilterAndPagination(Specification<Cab> specification, PaginationDetails paginationDetails) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cab> cq = cb.createQuery(Cab.class);
        Root<Cab> root = cq.from(Cab.class);

        root.fetch(Cab_.company);
        Fetch<Cab, Vehicle> v = root.fetch(Cab_.vehicle);
        v.fetch(Vehicle_.brand);

        root.get(Cab_.company);

        cq.select(root);
        cq.where(cb.and(specification.toPredicate(root, cq, cb)));
        TypedQuery<Cab> query = entityManager.createQuery(cq);

        query.setFirstResult(paginationDetails.getOffset());
        query.setMaxResults(paginationDetails.getLimit());

        return query.getResultList();
    }
}
