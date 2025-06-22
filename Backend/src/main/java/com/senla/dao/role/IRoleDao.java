package com.senla.dao.role;

import com.senla.model.role.Role;
import com.senla.util.dao.GenericDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface IRoleDao extends GenericDao<Role, Long> {
    Optional<Role> findByRoleIdExcludeAdmin(Long roleId);
}
