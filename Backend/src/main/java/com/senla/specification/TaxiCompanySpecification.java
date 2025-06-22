package com.senla.specification;

import com.senla.dto.taxicompany.TaxiCompanyFilterDto;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.model.taxicompany.TaxiCompany_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class TaxiCompanySpecification {

    public static Specification<TaxiCompany> nameLikePattern(String name) {
        return (Root<TaxiCompany> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(TaxiCompany_.name), "%" + name + "%");
        };
    }
    public static Specification<TaxiCompany> telephoneLikePattern(String telephone) {
        return (Root<TaxiCompany> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(TaxiCompany_.telephone), "%" + telephone + "%");
        };
    }
    public static Specification<TaxiCompany> parkCodeLikePattern(String parkCode) {
        return (Root<TaxiCompany> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(TaxiCompany_.parkCode), "%" + parkCode + "%");
        };
    }

    public static Specification<TaxiCompany> buildSpecification(TaxiCompanyFilterDto filterDto) {
        Specification<TaxiCompany> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (filterDto.getName() != null) {
            spec = spec.and(nameLikePattern(filterDto.getName()));
        }
        if (filterDto.getTelephone() != null) {
            spec = spec.and(telephoneLikePattern(filterDto.getTelephone()));
        }
        if (filterDto.getParkCode() != null) {
            spec = spec.and(parkCodeLikePattern(filterDto.getParkCode()));
        }
        return spec;
    }
}
