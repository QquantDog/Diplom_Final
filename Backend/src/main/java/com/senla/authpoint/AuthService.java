package com.senla.authpoint;

import com.senla.dao.role.RoleDao;
import com.senla.dao.user.UserDao;
import com.senla.dto.user.auth.LoginUserDto;
import com.senla.dto.user.auth.RegisterUserDto;
import com.senla.exception.DaoException;
import com.senla.model.driver.Driver;
import com.senla.model.role.Role;
import com.senla.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public User signup(RegisterUserDto registerUserDto) throws DaoException {

        Role role = roleDao.findByRoleIdExcludeAdmin(registerUserDto.getRoleId())
                .orElseThrow(() -> new DaoException("Driver/Customer Role not found"));


        User user = modelMapper.map(registerUserDto, User.class);
        user.setRole(role);
        user.setRegistrationDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        if(role.getRoleName().equals("driver")) {
            Driver driver = getEmptyDriverDetails();
            user.setDriver(driver);
            driver.setUser(user);
        }
        return userDao.create(user);
    }

    @Transactional
    public User login(LoginUserDto loginUserDto) throws DaoException {
        return userDao.findByEmail(
                loginUserDto.getEmail())
                    .orElseThrow(() -> new DaoException("Not found user wth such email")
        );
    }

    private Driver getEmptyDriverDetails(){
        return Driver.builder().isOnShift(false).isOnRide(false).build();
    }

}
