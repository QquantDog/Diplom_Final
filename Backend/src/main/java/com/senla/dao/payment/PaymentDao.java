package com.senla.dao.payment;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.payment.Payment;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PaymentDao extends AbstractLongDao<Payment> implements IPaymentDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Payment.class;
    }

    @Override
    public List<Payment> getPaymentsByCustomerId(Long customerId, PaginationDetails paginationDetails) {
        TypedQuery<Payment> q = em.createQuery("""
                        select p from Payment p
                        join fetch p.ride r
                        where r.customer.id = :customerId""", Payment.class);
        q.setParameter("customerId", customerId);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        return q.getResultList();
    }

    @Override
    public List<Payment> findAllWithRides(PaginationDetails paginationDetails) {
        TypedQuery<Payment> q = em.createQuery("""
                        select p from Payment p
                        join fetch p.ride r
                        """, Payment.class);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        return q.getResultList();
    }

    @Override
    public List<Payment> getPaymentsByDriverId(Long driverId, PaginationDetails paginationDetails) {
        TypedQuery<Payment> q = em.createQuery("""
                        select p from Payment p
                        join fetch p.ride r
                        join r.shift s
                        join s.driver d
                        where d.id = :driverId
                        """, Payment.class);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }
}