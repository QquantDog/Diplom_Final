package com.senla.model.role;

import com.senla.model.privilege.Privilege;
import com.senla.model.user.User;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role implements Identifiable<Long> {
    @Id
    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    @Column(name = "role_name", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private String roleName;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "roles_privileges_join",
        joinColumns = { @JoinColumn(name = "role_id")},
        inverseJoinColumns = { @JoinColumn(name = "privilege_id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Privilege> privileges = new HashSet<>();


    @Override
    @Transient
    public Long getId() {
        return roleId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.roleId = aLong;
    }
}
