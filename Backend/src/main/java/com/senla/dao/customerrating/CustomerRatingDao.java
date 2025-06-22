package com.senla.dao.customerrating;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.customerrating.CustomerRating;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRatingDao extends AbstractLongDao<CustomerRating> implements ICustomerRatingDao {

    @Override
    @PostConstruct
    protected void init() {
        super.clazz = CustomerRating.class;
    }


    @Override
    public List<CustomerRating> findAllRatingsWithRides(PaginationDetails paginationDetails) {
        TypedQuery<CustomerRating> q = em.createQuery("""
                                            select cr from CustomerRating cr
                                            join fetch cr.ride r
                                            """, CustomerRating.class);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        return q.getResultList();
    }

    @Override
    public List<CustomerRating> findCustomerRatingsWithRides(Long customerId, PaginationDetails paginationDetails) {
        TypedQuery<CustomerRating> q = em.createQuery("""
                                            select cr from CustomerRating cr
                                            join fetch cr.ride r
                                            where r.customer.id = :customerId
                                            """, CustomerRating.class);
        q.setFirstResult(paginationDetails.getOffset());
        q.setMaxResults(paginationDetails.getLimit());
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

    @Override
    public Optional<CustomerRating> findRatingByIdWithRides(Long ratingId) {
        TypedQuery<CustomerRating> q = em.createQuery("""
                                            select cr from CustomerRating cr
                                            join fetch cr.ride r
                                            where cr.id = :ratingId
                                            """, CustomerRating.class);
        q.setParameter("ratingId", ratingId);
        return q.getResultStream().findFirst();
    }

    @Override
    public Optional<CustomerRating> findCustomerRatingByIdWithRides(Long ratingId, Long customerId) {
        TypedQuery<CustomerRating> q = em.createQuery("""
                                            select cr from CustomerRating cr
                                            join fetch cr.ride r
                                            where cr.id = :ratingId
                                                and r.customer.id = :customerId
                                            """, CustomerRating.class);
        q.setParameter("ratingId", ratingId);
        q.setParameter("customerId", customerId);
        return q.getResultStream().findFirst();
    }
}

