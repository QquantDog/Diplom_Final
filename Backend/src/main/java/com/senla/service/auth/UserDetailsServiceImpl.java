package com.senla.service.auth;

import com.senla.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userDao.findUserByEmailWithAuthorities(email).orElseThrow(() -> new UsernameNotFoundException(email));
        List<? extends GrantedAuthority> authorities = user.getRole().getPrivileges().stream().map((p) -> {
            return new SimpleGrantedAuthority(p.getPrivilegeCode());
        }).toList();
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
