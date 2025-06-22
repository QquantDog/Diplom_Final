package com.senla.model.driverregistry;

import com.senla.model.driver.Driver;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "driver_registry")
public class DriverRegistry implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_date")
    @EqualsAndHashCode.Exclude
    private LocalDate registrationDate;

    @Column(name = "registration_expiration_date")
    @EqualsAndHashCode.Exclude
    private LocalDate registrationExpirationDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TaxiCompany taxiCompany;


    @Override
    @Transient
    public Long getId() {
        return id;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.id = aLong;
    }
}
