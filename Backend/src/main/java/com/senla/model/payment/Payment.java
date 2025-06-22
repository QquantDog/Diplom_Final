package com.senla.model.payment;

import com.senla.model.ride.Ride;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;


    @Column(name = "method", nullable = false)
    @ColumnTransformer(write = "?::payment_method_type")
    @EqualsAndHashCode.Exclude
    private String method;

    @Column(name = "overall_price")
    @EqualsAndHashCode.Exclude
    private BigDecimal overallPrice;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ride ride;


    @Override
    @Transient
    public Long getId() {
        return paymentId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.paymentId = aLong;
    }
}
