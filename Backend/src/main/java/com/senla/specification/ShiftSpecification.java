package com.senla.specification;

import com.senla.model.shift.Shift;
import com.senla.model.shift.Shift_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ShiftSpecification {
    public static Specification<Shift> startTimeFrom(LocalDateTime startFrom) {
        return (Root<Shift> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.greaterThanOrEqualTo(root.get(Shift_.starttime), startFrom);
        };
    }

    public static Specification<Shift> startTimeTo(LocalDateTime startTo) {
        return (Root<Shift> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.lessThan(root.get(Shift_.starttime), startTo);
        };
    }
    public static Specification<Shift> endTimeFrom(LocalDateTime endFrom) {
        return (Root<Shift> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.greaterThanOrEqualTo(root.get(Shift_.endtime), endFrom);
        };
    }

    public static Specification<Shift> complexEndTimePredicates(LocalDateTime endTo, boolean isActive) {
        return (Root<Shift> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate to = cb.conjunction();
            if(isActive) {
                to = cb.isNull(root.get(Shift_.endtime));
            } else{
                if(endTo != null) {
                    to = cb.lessThan(root.get(Shift_.endtime), endTo);
                }
            }
            return cb.and(to);
        };
    }

    public static Specification<Shift> buildSpecification(LocalDateTime startFrom, LocalDateTime startTo, LocalDateTime endFrom, LocalDateTime endTo, boolean isActive) {
        Specification<Shift> spec = Specification.where(null);
        if (startFrom != null) {
            spec = spec.and(ShiftSpecification.startTimeFrom(startFrom));
        }
        if (startTo != null) {
            spec = spec.and(ShiftSpecification.startTimeTo(startTo));
        }
        if(endFrom != null) {
            spec = spec.and(ShiftSpecification.endTimeFrom(endFrom));
        }
        spec = spec.and(ShiftSpecification.complexEndTimePredicates(endTo, isActive));
        return spec;
    }
}