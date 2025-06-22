package com.senla.model.tier;

import com.senla.model.rate.Rate;
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
@Table(name = "tiers")
public class Tier implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long tierId;

    @Column(name = "tier_name", nullable = false)
    @EqualsAndHashCode.Exclude
    private String tierName;

    @Column
    @EqualsAndHashCode.Exclude
    private String description;

    @OneToMany(mappedBy = "tier")
    @EqualsAndHashCode.Exclude
    private Set<Rate> rates = new HashSet<>();

    @OneToMany(mappedBy = "tier")
    @EqualsAndHashCode.Exclude
    private Set<Vehicle> vehicles = new HashSet<>();


    @Override
    @Transient
    public Long getId() {
        return tierId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.tierId = aLong;
    }
}
