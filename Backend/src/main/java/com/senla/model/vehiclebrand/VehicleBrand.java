package com.senla.model.vehiclebrand;

import com.senla.model.vehicle.Vehicle;
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
@Table(name = "vehicle_brands")
public class VehicleBrand implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name")
    @EqualsAndHashCode.Exclude
    private String brandName;

    @OneToMany(mappedBy = "brand")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Vehicle> vehicles = new HashSet<>();


    @Override
    @Transient
    public Long getId() {
        return brandId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.brandId = aLong;
    }
}
