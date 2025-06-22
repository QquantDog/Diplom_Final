package com.senla.model.city;

import com.senla.model.rate.Rate;
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
@Table(name = "cities")
public class City implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city_name", nullable = false)
    @EqualsAndHashCode.Exclude
    private String cityName;

    @OneToMany(mappedBy = "city")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Rate> rates = new HashSet<>();

    @Override
    @Transient
    public Long getId() {
        return this.cityId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.cityId = aLong;
    }
}
