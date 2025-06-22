package com.senla.dao.promocode;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.promocode.Promocode;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromocodeDao extends AbstractLongDao<Promocode> implements IPromocodeDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Promocode.class;
    }

    @Override
    public List<Promocode> findBySpecification(Specification<Promocode> promocodeSpecification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Promocode> cq = cb.createQuery(Promocode.class);
        Root<Promocode> root = cq.from(Promocode.class);

        cq.select(root);
        if(promocodeSpecification != null){
            cq.where(promocodeSpecification.toPredicate(root, cq , cb));
        }
        TypedQuery<Promocode> query = em.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public Promocode findByCode(String code) {
        TypedQuery<Promocode> q = em.createQuery("""
            select p from Promocode p
            where p.promocodeCode ilike :code""", Promocode.class);
        q.setParameter("code", code);
        return q.getSingleResult();
    }

    @Override
    public List<Promocode> findAllWithOffsetAndLimit(PaginationDetails paginationDetails) {
        TypedQuery<Promocode> q = em.createQuery("""
            select p from Promocode p
            """,Promocode.class);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        return q.getResultList();
    }
}
