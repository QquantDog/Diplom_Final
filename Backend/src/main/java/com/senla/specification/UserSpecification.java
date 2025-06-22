package com.senla.specification;

import com.senla.dto.user.UserFilterDto;
import com.senla.model.user.User;
import com.senla.model.user.User_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserSpecification {

    public static Specification<User> registrationInterval(LocalDateTime dayFrom, LocalDateTime dayTo) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate dayFromPredicate = cb.conjunction();
            Predicate dayToPredicate = cb.conjunction();
            if(dayFrom != null) dayFromPredicate = cb.greaterThanOrEqualTo(root.get(User_.registrationDate), dayFrom);
            if(dayTo   != null) dayToPredicate   = cb.lessThanOrEqualTo(root.get(User_.registrationDate), dayTo);
            return cb.and(dayFromPredicate, dayToPredicate);
        };
    }

    public static Specification<User> firstNameLikePattern(String firstName) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(User_.firstName), "%" + firstName + "%");
        };
    }
    public static Specification<User> lastNameLikePattern(String lastName) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(User_.lastName), "%" + lastName + "%");
        };
    }
    public static Specification<User> emailLikePattern(String email) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(User_.email), "%" + email + "%");
        };
    }

    public static Specification<User> buildSpecification(UserFilterDto filterDto) {
        Specification<User> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (filterDto.getFirstName() != null) {
            spec = spec.and(UserSpecification.firstNameLikePattern(filterDto.getFirstName()));
        }
        if (filterDto.getLastName() != null) {
            spec = spec.and(UserSpecification.lastNameLikePattern(filterDto.getLastName()));
        }
        if (filterDto.getEmail() != null) {
            spec = spec.and(UserSpecification.emailLikePattern(filterDto.getEmail()));
        }
        if(filterDto.getRegistrationDateMin() != null || filterDto.getRegistrationDateMax() != null) {
            spec = spec.and(UserSpecification.registrationInterval(filterDto.getRegistrationDateMin(), filterDto.getRegistrationDateMax()));
        }
        return spec;
    }
}