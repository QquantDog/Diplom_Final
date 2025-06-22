package com.senla.service.user;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.user.UserFilterDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.model.user.User;
import com.senla.util.service.GenericService;

import java.util.List;

public interface UserService extends GenericService<User, Long> {

    User updateUser(UserUpdateDto dto);
    User findUserByEmailWithAuthorities(String email);
    User findMe();

    List<User> findAllWithSearchAndPagination(UserFilterDto filterDto, PaginationRequest paginationRequest);
}
