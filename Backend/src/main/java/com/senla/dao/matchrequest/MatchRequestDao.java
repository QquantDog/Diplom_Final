package com.senla.dao.matchrequest;


import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.matchrequest.MatchRequest;
import com.senla.model.matchrequest.MatchRequest_;
import com.senla.types.MatchRequestStatus;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MatchRequestDao extends AbstractLongDao<MatchRequest> implements IMatchRequestDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = MatchRequest.class;
    }

    @Override
    public Optional<MatchRequest> matchRequestRideAndShift(Long rideId, Long shiftId) {
        TypedQuery<MatchRequest> q = em.createQuery("""
                        select m from MatchRequest m
                        join m.ride r
                        join m.shift s
                        where r.id = :rideId
                            and s.id = :shiftId
                            and m.matchRequestStatus = :offeredStatus
                        """, MatchRequest.class);
        q.setParameter("offeredStatus", MatchRequestStatus.OFFERED);
        q.setParameter("rideId", rideId);
        q.setParameter("shiftId", shiftId);
        return q.getResultStream().findFirst();
    }

    @Override
    public void evictOtherMatchRequestsForRide(Long rideId, Long shiftId) {
        Query q = em.createQuery("""
                        update MatchRequest m set m.matchRequestStatus = :stale
                            where m.ride.id = :rideId
                                and m.shift.id != :shiftId
                                and m.matchRequestStatus = :offeredStatus
                        """);
        q.setParameter("offeredStatus", MatchRequestStatus.OFFERED);
        q.setParameter("stale", MatchRequestStatus.STALE);
        q.setParameter("rideId", rideId);
        q.setParameter("shiftId", shiftId);

        q.executeUpdate();
    }
    @Override
    public void evictAllMatchRequestsForRide(Long rideId) {
        Query q = em.createQuery("""
                        update MatchRequest m set m.matchRequestStatus = :stale
                            where m.ride.id = :rideId
                                and m.matchRequestStatus = :offeredStatus
                        """);
        q.setParameter("offeredStatus", MatchRequestStatus.OFFERED);
        q.setParameter("stale", MatchRequestStatus.STALE);
        q.setParameter("rideId", rideId);

        q.executeUpdate();
    }

    @Override
    public List<MatchRequest> findMatchRequests(PaginationDetails paginationDetails) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MatchRequest> cq = cb.createQuery(MatchRequest.class);
        Root<MatchRequest> root = cq.from(MatchRequest.class);

        root.fetch(MatchRequest_.ride);
        root.fetch(MatchRequest_.shift);


        cq.select(root);
        TypedQuery<MatchRequest> query = em.createQuery(cq);

        query.setFirstResult(paginationDetails.getOffset());
        query.setMaxResults(paginationDetails.getLimit());

        return query.getResultList();
    }

    @Override
    public Integer deactivateRequest(Long requestId) {
        Query q = em.createQuery("""
                        update MatchRequest m set m.matchRequestStatus = :stale
                            where m.id = :requestId
                                and m.matchRequestStatus = :offeredStatus
                        """);
        q.setParameter("offeredStatus", MatchRequestStatus.OFFERED);
        q.setParameter("stale", MatchRequestStatus.STALE);
        q.setParameter("requestId", requestId);

        return q.executeUpdate();
    }

    @Override
    public List<MatchRequest> matchRequestsToCheckForOfferStatus(Long requestId) {
        TypedQuery<MatchRequest> q = em.createQuery("""
                        select m from MatchRequest m
                        join m.ride r
                        where m.id != :requestId
                            and m.matchRequestStatus = :offeredStatus
                            and m.ride.id = r.id
                        """, MatchRequest.class);
        q.setParameter("offeredStatus", MatchRequestStatus.OFFERED);
        q.setParameter("requestId", requestId);
        return q.getResultList();
    }

}
