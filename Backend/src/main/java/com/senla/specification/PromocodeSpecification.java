package com.senla.specification;

import com.senla.dto.promocode.PromocodeFilterDto;
import com.senla.model.promocode.Promocode;
import com.senla.model.promocode.Promocode_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PromocodeSpecification {

    public static Specification<Promocode> startDateInterval(LocalDate startDateFrom, LocalDate startDateTo) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate startDateFromPredicate = cb.conjunction();
            Predicate startDateToPredicate = cb.conjunction();
            if(startDateFrom != null) startDateFromPredicate = cb.greaterThanOrEqualTo(root.get(Promocode_.startDate), startDateFrom);
            if(startDateTo   != null) startDateToPredicate = cb.lessThanOrEqualTo(root.get(Promocode_.startDate), startDateTo);
            return cb.and(startDateFromPredicate, startDateToPredicate);
        };
    }

    public static Specification<Promocode> endDateInterval(LocalDate endDateFrom, LocalDate endDateTo) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate endDateFromPredicate = cb.conjunction();
            Predicate endDateToPredicate = cb.conjunction();
            if(endDateFrom != null) endDateFromPredicate = cb.greaterThanOrEqualTo(root.get(Promocode_.endDate), endDateFrom);
            if(endDateTo   != null) endDateToPredicate   = cb.lessThanOrEqualTo(root.get(Promocode_.endDate), endDateTo);
            return cb.and(endDateFromPredicate, endDateToPredicate);
        };
    }

    public static Specification<Promocode> discountInterval(BigDecimal discountFrom, BigDecimal discountTo) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate discountFromPredicate = cb.conjunction();
            Predicate discountToPredicate = cb.conjunction();
            if(discountFrom != null) discountFromPredicate = cb.greaterThanOrEqualTo(root.get(Promocode_.discountValue), discountFrom);
            if(discountTo   != null) discountToPredicate   = cb.lessThanOrEqualTo(root.get(Promocode_.discountValue), discountTo);
            return cb.and(discountFromPredicate, discountToPredicate);
        };
    }

    public static Specification<Promocode> promocodeLikePattern(String promocodeText) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(Promocode_.promocodeCode), "%" + promocodeText + "%");
        };
    }

    public static Specification<Promocode> buildSpecification(PromocodeFilterDto filterDto) {
        Specification<Promocode> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (filterDto.getPromocodeCode() != null) {
            spec = spec.and(PromocodeSpecification.promocodeLikePattern(filterDto.getPromocodeCode()));
        }
        if (filterDto.getStartDateMin() != null || filterDto.getStartDateMax() != null) {
            spec = spec.and(PromocodeSpecification.startDateInterval(filterDto.getStartDateMin(), filterDto.getStartDateMax()));
        }
        if(filterDto.getEndDateMin() != null || filterDto.getEndDateMax() != null) {
            spec = spec.and(PromocodeSpecification.endDateInterval(filterDto.getEndDateMin(), filterDto.getEndDateMax()));
        }
        if(filterDto.getDiscountMin() != null || filterDto.getDiscountMax() != null) {
            spec = spec.and(PromocodeSpecification.discountInterval(filterDto.getDiscountMin(), filterDto.getDiscountMax()));
        }
        return spec;
    }
}