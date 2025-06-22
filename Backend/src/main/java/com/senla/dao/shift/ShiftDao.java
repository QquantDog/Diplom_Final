package com.senla.dao.shift;

import com.senla.model.shift.Shift;
import com.senla.types.MatchRequestStatus;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShiftDao extends AbstractLongDao<Shift> implements IShiftDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Shift.class;
    }

    @Override
    public List<Shift> getAllShifts() {
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
            join fetch s.cab cab
            join fetch s.driver d
            join fetch s.rate r
            """, Shift.class);
        return q.getResultList();
    }

    @Override
    public List<Shift> getDriverShifts(Long driverId) {
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
            join fetch s.cab cab
            join fetch s.driver d
            join fetch s.rate r
            join fetch r.city c
            join fetch cab.vehicle v
            join fetch v.tier t
            where d.id = :driverId
            """, Shift.class);
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }

    @Override
    public List<Shift> getDriverActiveShifts(Long driverId) {
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
            join fetch s.driver d
            join fetch s.rate r
            join fetch s.cab cab
            where d.id = :driverId
                and s.endtime is null
            """, Shift.class);
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }


    @Override
    public List<Tuple> getMatchingShifts(Long rateId, Point customerStartPoint, Double radiusThresholdMeters){
        TypedQuery<Tuple> q = em.createQuery("""
            select s, ST_Distance(st_transform(:customerStartPoint, 3857), st_transform(d.currentPoint, 3857))  from Shift s
            join fetch s.driver d
            where s.rate.rateId = :rateId
                and d.isOnShift = true
                and d.isOnRide = false
                and ST_Distance(st_transform(:customerStartPoint, 3857), st_transform(d.currentPoint, 3857)) < :radiusThresholdMeters
                and s.shiftId not in (select mr.shift.id from MatchRequest mr
                                        where mr.matchRequestStatus in :offered)
            order by ST_Distance(st_transform(:customerStartPoint, 3857), st_transform(d.currentPoint, 3857)) asc
            """, Tuple.class);
        q.setParameter("rateId", rateId);
        q.setParameter("customerStartPoint", customerStartPoint);
        q.setParameter("radiusThresholdMeters", radiusThresholdMeters);
        q.setParameter("offered", MatchRequestStatus.OFFERED);
        return q.getResultList();
    }

    @Override
    public Optional<Shift> getActiveShift(Long driverId) {
        TypedQuery<Shift> q = em.createQuery("""
                select s from Shift s
                where s.endtime is null
                    and s.driver.driverId = :driverId
                """, Shift.class);
        q.setParameter("driverId", driverId);
        return q.getResultList().stream().findFirst();
    }

    public int get5(){
        return 5;
    }
}
