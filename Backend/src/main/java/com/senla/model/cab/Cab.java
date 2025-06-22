package com.senla.model.cab;

import com.senla.model.taxicompany.TaxiCompany;
import com.senla.model.vehicle.Vehicle;
import com.senla.util.Identifiable;
import org.hibernate.annotations.Cache;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cabs")
@Cache(region = "defaultCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cab implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cab_id")
    private Long cabId;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
    private String vin;

    @Column(name = "is_on_shift", nullable = false)
    @EqualsAndHashCode.Exclude
    private Boolean isOnShift;

    @Column(name = "manufacture_date", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDate manufactureDate;

    @Column(name = "color_description", nullable = false)
    @EqualsAndHashCode.Exclude
    private String colorDescription;

    @Column(name = "license_plate", nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
    private String licensePlate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_company_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TaxiCompany company;


    @Override
    @Transient
    public Long getId() {
        return cabId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.cabId = aLong;
    }
}
