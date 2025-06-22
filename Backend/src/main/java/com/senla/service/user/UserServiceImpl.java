package com.senla.service.user;

import com.senla.dao.role.RoleDao;
import com.senla.dao.user.UserDao;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.user.UserFilterDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.user.User;
import com.senla.specification.UserSpecification;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserServiceImpl extends AbstractLongIdGenericService<User> implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;


    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = userDao;
    }


    @Override
    @Transactional
    public User updateUser(UserUpdateDto dto) {
        User user = userDao.findMe(SecUtils.extractId())
                .orElseThrow(()->new DaoException("Can't find user by id" + SecUtils.extractId()));
        modelMapper.map(dto, user);
        return userDao.update(user);
    }

    @Override
    @Transactional
    public User findUserByEmailWithAuthorities(String email) {
        return userDao.findUserByEmailWithAuthorities(email).orElse(null);
    }

    @Override
    @Transactional
    public User findMe() {
        return userDao.findMe(SecUtils.extractId())
                .orElseThrow(()->new DaoException("Can't find user by id" + SecUtils.extractId()));
    }

    @Override
    public List<User> findAllWithSearchAndPagination(UserFilterDto userFilterDto, PaginationRequest paginationRequest) {
        return userDao.getAllWithFilterAndPagination(UserSpecification.buildSpecification(userFilterDto), PaginationUtils
                .getOffsetByCountAndParams((int) userDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }
}
