package com.senla.specification;

import com.senla.dto.rate.RateFilterDto;
import com.senla.model.rate.Rate;
import com.senla.model.rate.Rate_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RateSpecification {

    public static Specification<Rate> rateInitPriceInterval(BigDecimal initPriceFrom, BigDecimal initPriceTo) {
        return (Root<Rate> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate initPriceFromPredicate = cb.conjunction();
            Predicate initPriceToPredicate = cb.conjunction();
            if(initPriceFrom != null) initPriceFromPredicate = cb.greaterThanOrEqualTo(root.get(Rate_.initPrice), initPriceFrom);
            if(initPriceTo   != null) initPriceToPredicate   = cb.lessThanOrEqualTo(root.get(Rate_.initPrice), initPriceTo);
            return cb.and(initPriceFromPredicate, initPriceToPredicate);
        };
    }

    public static Specification<Rate> ratePerKmInterval(BigDecimal ratePerKmFrom, BigDecimal ratePerKmTo) {
        return (Root<Rate> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate ratePerKmFromPredicate = cb.conjunction();
            Predicate ratePerKmToPredicate = cb.conjunction();
            if(ratePerKmFrom != null) ratePerKmFromPredicate = cb.greaterThanOrEqualTo(root.get(Rate_.ratePerKm), ratePerKmFrom);
            if(ratePerKmTo   != null) ratePerKmToPredicate   = cb.lessThanOrEqualTo(root.get(Rate_.ratePerKm), ratePerKmTo);
            return cb.and(ratePerKmFromPredicate, ratePerKmToPredicate);
        };
    }

    public static Specification<Rate> rateFreeTimeInterval(Integer freeTimeFrom, Integer freeTimeTo) {
        return (Root<Rate> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate freeTimeFromPredicate = cb.conjunction();
            Predicate freeTimeToPredicate = cb.conjunction();
            if(freeTimeFrom != null) freeTimeFromPredicate = cb.greaterThanOrEqualTo(root.get(Rate_.freeTimeInSeconds), freeTimeFrom);
            if(freeTimeTo   != null) freeTimeToPredicate   = cb.lessThanOrEqualTo(root.get(Rate_.freeTimeInSeconds), freeTimeTo);
            return cb.and(freeTimeFromPredicate, freeTimeToPredicate);
        };
    }

    public static Specification<Rate> ratePaidWaiting(BigDecimal paidWaitingFrom, BigDecimal paidWaitingTo) {
        return (Root<Rate> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate paidWaitingFromPredicate = cb.conjunction();
            Predicate paidWaitingToPredicate = cb.conjunction();
            if(paidWaitingFrom != null) paidWaitingFromPredicate = cb.greaterThanOrEqualTo(root.get(Rate_.paidWaitingPerMinute), paidWaitingFrom);
            if(paidWaitingTo   != null) paidWaitingToPredicate   = cb.lessThanOrEqualTo(root.get(Rate_.paidWaitingPerMinute), paidWaitingTo);
            return cb.and(paidWaitingToPredicate, paidWaitingFromPredicate);
        };
    }

    public static Specification<Rate> buildSpecification(RateFilterDto filterDto) {
        Specification<Rate> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (filterDto.getInitPriceMin() != null || filterDto.getInitPriceMax() != null) {
            spec = spec.and(RateSpecification.rateInitPriceInterval(filterDto.getInitPriceMin(), filterDto.getInitPriceMax()));
        }
        if (filterDto.getRatePerKmMin() != null || filterDto.getRatePerKmMax() != null) {
            spec = spec.and(RateSpecification.ratePerKmInterval(filterDto.getRatePerKmMin(), filterDto.getRatePerKmMax()));
        }
        if (filterDto.getFreeTimeInSecondsMin() != null || filterDto.getFreeTimeInSecondsMax() != null) {
            spec = spec.and(RateSpecification.rateFreeTimeInterval(filterDto.getFreeTimeInSecondsMin(), filterDto.getFreeTimeInSecondsMax()));
        }
        if (filterDto.getPaidWaitingPerMinuteMin() != null || filterDto.getPaidWaitingPerMinuteMax() != null) {
            spec = spec.and(RateSpecification.ratePaidWaiting(filterDto.getPaidWaitingPerMinuteMin(), filterDto.getPaidWaitingPerMinuteMax()));
        }
        return spec;
    }
}
