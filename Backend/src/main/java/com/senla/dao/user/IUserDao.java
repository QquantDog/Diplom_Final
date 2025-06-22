package com.senla.dao.user;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.user.User;
import com.senla.util.dao.GenericDao;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IUserDao extends GenericDao<User, Long> {
    Optional<User> findByEmail(String email);
    User findCustomer(Long userId);
    Optional<User> findUserByEmailWithAuthorities(String email);
    Optional<User> findMe(Long id);
    List<User> getAllWithFilterAndPagination(Specification<User> specification, PaginationDetails paginationDetails);
}
