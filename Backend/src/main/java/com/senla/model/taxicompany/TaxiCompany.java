package com.senla.model.taxicompany;

import com.senla.model.cab.Cab;
import com.senla.model.driverregistry.DriverRegistry;
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
@Table(name = "taxi_companies")
public class TaxiCompany implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name", nullable = false)
    @EqualsAndHashCode.Exclude
    private String name;

    @Column(name="telephone", nullable = false)
    @EqualsAndHashCode.Exclude
    private String telephone;

    @Column(name="park_code")
    @EqualsAndHashCode.Exclude
    private String parkCode;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Cab> cabs = new HashSet<>();

    @OneToMany(mappedBy = "taxiCompany", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DriverRegistry> registrationEntries = new HashSet<>();


    @Override
    @Transient
    public Long getId() {
        return companyId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.companyId = aLong;
    }
}
