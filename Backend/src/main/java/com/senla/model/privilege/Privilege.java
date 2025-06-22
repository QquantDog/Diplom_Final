package com.senla.model.privilege;

import com.senla.model.role.Role;
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
@Table(name = "privileges")
public class Privilege implements Identifiable<Long> {
    @Id
    @Column(name = "privilege_id", insertable = false, updatable = false)
    private Long privilegeId;

    @Column(name = "privilege_code", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private String privilegeCode;

    @ManyToMany(mappedBy = "privileges")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();


    @Override
    @Transient
    public Long getId() {
        return privilegeId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.privilegeId = aLong;
    }
}
