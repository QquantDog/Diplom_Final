package com.senla.model.promocode;

import com.senla.model.ride.Ride;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promocodes")
public class Promocode implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promocode_id")
    private Long promocodeId;

    @Column(name = "promocode_code", nullable = false)
    @EqualsAndHashCode.Exclude
    private String promocodeCode;

    @Column(name = "discount_value", nullable = false)
    @EqualsAndHashCode.Exclude
    private BigDecimal discountValue;

    @Column(name = "start_date", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDate endDate;

    @Column
    @EqualsAndHashCode.Exclude
    private String description;


    @OneToMany(mappedBy = "promocode")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ride> rides = new HashSet<>();


    @Override
    @Transient
    public Long getId() {
        return promocodeId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.promocodeId = aLong;
    }
}
