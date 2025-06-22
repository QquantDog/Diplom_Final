package com.senla.dao.taxicompany;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaxiCompanyDao extends AbstractLongDao<TaxiCompany> implements ITaxiCompanyDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = TaxiCompany.class;
    }

    @Override
    public List<TaxiCompany> getAllWithCabs() {
        TypedQuery<TaxiCompany> q = em.createQuery("""
                SELECT t FROM TaxiCompany t
                join fetch t.cabs c
                join fetch c.vehicle v
                join fetch v.brand
                """, TaxiCompany.class);
        return q.getResultList();
    }

    @Override
    public Optional<TaxiCompany> getCompanyWithCabs(Long companyId) {
        TypedQuery<TaxiCompany> q = em.createQuery("""
                SELECT t FROM TaxiCompany t
                join fetch t.cabs c
                join fetch c.vehicle v
                join fetch v.brand
                where t.id = :companyId""", TaxiCompany.class);
        q.setParameter("companyId", companyId);
        return q.getResultStream().findFirst();
    }

    @Override
    public List<TaxiCompany> getAllWithFilterAndPagination(Specification<TaxiCompany> specification, PaginationDetails paginationDetails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TaxiCompany> cq = cb.createQuery(TaxiCompany.class);
        Root<TaxiCompany> root = cq.from(TaxiCompany.class);

        cq.select(root);
        if(specification != null){
            cq.where(specification.toPredicate(root, cq , cb));
        }
        TypedQuery<TaxiCompany> query = em.createQuery(cq);
        query.setFirstResult(paginationDetails.getOffset());
        query.setMaxResults(paginationDetails.getLimit());

        return query.getResultList();
    }
}