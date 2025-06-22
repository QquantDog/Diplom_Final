package com.senla.dao.rate;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.rate.Rate;
import com.senla.model.rate.Rate_;
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
public class RateDao extends AbstractLongDao<Rate> implements IRateDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Rate.class;
    }

    @Override
    public Optional<Rate> getAutomaticRateForDriver(Long cabId, Long cityId){
        TypedQuery<Rate> q = em.createQuery(
                """
                        select r from Rate r
                        join r.city city
                        join r.tier t
                        join t.vehicles v
                        join v.cabs c
                        where c.id=:cabId
                            and city.id=:cityId
                        """, Rate.class);
        q.setParameter("cabId", cabId);
        q.setParameter("cityId", cityId);
        return q.getResultStream().findFirst();
    }

    @Override
    public List<Rate> getAllWithFilterAndPagination(Specification<Rate> specification, PaginationDetails paginationDetails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rate> cq = cb.createQuery(Rate.class);
        Root<Rate> root = cq.from(Rate.class);

        root.fetch(Rate_.city);
        root.fetch(Rate_.tier);

        cq.select(root);
        if(specification != null){
            cq.where(specification.toPredicate(root, cq , cb));
        }

        TypedQuery<Rate> query = em.createQuery(cq);
        query.setFirstResult(paginationDetails.getOffset());
        query.setMaxResults(paginationDetails.getLimit());

        return query.getResultList();
    }


    @Override
    public List<Rate> getRatesFull() {
        TypedQuery<Rate> q = em.createQuery(
                """
                        select r from Rate r
                        join fetch r.city c
                        join fetch r.tier t
                        """, Rate.class);
        return q.getResultList();
    }

    @Override
    public Optional<Rate> getRateById(Long rateId) {
        TypedQuery<Rate> q = em.createQuery(
                """
                        select r from Rate r
                        join fetch r.city c
                        join fetch r.tier t
                        where r.id=:rateId
                        """, Rate.class);
        q.setParameter("rateId", rateId);
        return q.getResultStream().findFirst();
    }
}
