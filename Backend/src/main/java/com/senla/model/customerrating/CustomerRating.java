package com.senla.model.customerrating;

import com.senla.model.ride.Ride;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_ratings")
public class CustomerRating implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @Column()
    @EqualsAndHashCode.Exclude
    private BigDecimal rating;

    @Column(name = "created_at", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime updatedAt;

    @Column()
    @EqualsAndHashCode.Exclude
    private String comment;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ride ride;


    @Override
    @Transient
    public Long getId() {
        return ratingId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.ratingId = aLong;
    }
}
