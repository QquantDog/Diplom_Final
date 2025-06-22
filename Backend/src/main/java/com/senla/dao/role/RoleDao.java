package com.senla.dao.role;

import com.senla.model.role.Role;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleDao extends AbstractLongDao<Role> implements IRoleDao{

    @Override
    @PostConstruct
    protected void init() {
        this.clazz = Role.class;
    }

    @Override
    public Optional<Role> findByRoleIdExcludeAdmin(Long roleId) {
        TypedQuery<Role> q = em.createQuery("""
                                SELECT r FROM Role r
                                WHERE r.id = :roleId
                                    and r.roleName not ilike 'admin'
                                """, Role.class);

        q.setParameter("roleId", roleId);
        return q.getResultStream().findFirst();
    }
}
