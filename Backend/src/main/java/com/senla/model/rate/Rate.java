package com.senla.model.rate;

import com.senla.model.city.City;
import com.senla.model.tier.Tier;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rates")
public class Rate implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long rateId;

    @Column(name = "init_price", nullable = false)
    @EqualsAndHashCode.Exclude
    private BigDecimal initPrice;

    @Column(name = "rate_per_km", nullable = false)
    @EqualsAndHashCode.Exclude
    private BigDecimal ratePerKm;

    @Column(name = "paid_waiting_per_minute", nullable = false)
    @EqualsAndHashCode.Exclude
    private BigDecimal paidWaitingPerMinute;

    @Column(name = "free_time_in_seconds", nullable = false)
    @EqualsAndHashCode.Exclude
    private Integer freeTimeInSeconds;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_tier_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tier tier;


    @Override
    @Transient
    public Long getId() {
        return rateId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.rateId = aLong;
    }
}
