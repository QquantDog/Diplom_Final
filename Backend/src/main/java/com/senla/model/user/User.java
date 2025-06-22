package com.senla.model.user;

import com.senla.model.driver.Driver;
import com.senla.model.ride.Ride;
import com.senla.model.role.Role;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    @EqualsAndHashCode.Exclude
    private String firstName;

    @Column(name = "last_name")
    @EqualsAndHashCode.Exclude
    private String lastName;

    @Column(name = "email", nullable = false)
    @EqualsAndHashCode.Exclude
    private String email;

    @Column(name = "hashed_password", nullable = false)
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "phone_number", nullable = false)
    @EqualsAndHashCode.Exclude
    private String phoneNumber;

    @Column(name = "registration_date", updatable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime registrationDate;

    @OneToOne(mappedBy = "user", optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Driver driver;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ride> rides = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Role role;


    @Override
    @Transient
    public Long getId() {
        return userId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.userId = aLong;
    }
}
