package com.senla.model.ride;

import com.senla.model.customerrating.CustomerRating;
import com.senla.model.matchrequest.MatchRequest;
import com.senla.model.payment.Payment;
import com.senla.model.promocode.Promocode;
import com.senla.model.rate.Rate;
import com.senla.model.shift.Shift;
import com.senla.model.user.User;
import com.senla.types.RideStatus;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.locationtech.jts.geom.Point;
import org.hibernate.annotations.Cache;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rides")
@NamedEntityGraphs({
    @NamedEntityGraph(name = "rides-with-ratings",
            attributeNodes = {
                @NamedAttributeNode("customerRating")
            }),
    @NamedEntityGraph(name = "rides-with-shifts-and-cabs-and-customers",
            attributeNodes = {
                @NamedAttributeNode(value = "shift", subgraph = "shifts_cabs_subgraph"),
                @NamedAttributeNode("customer")
            },
            subgraphs = {
                @NamedSubgraph(name = "shifts_cabs_subgraph", attributeNodes = {
                        @NamedAttributeNode("cab")
                })
            }
    )
})
@Cache(region = "defaultCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ride implements Identifiable<Long>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ride_id")
    private Long rideId;

    @Column(name = "ride_tip")
    @EqualsAndHashCode.Exclude
    private BigDecimal rideTip;

    @Column(name = "ride_distance_expected_meters", nullable = false)
    @EqualsAndHashCode.Exclude
    private BigDecimal rideDistanceExpectedMeters;

    @Column(name = "ride_distance_actual_meters")
    @EqualsAndHashCode.Exclude
    private BigDecimal rideDistanceActualMeters;

    @Column(name = "start_point", nullable = false)
    @EqualsAndHashCode.Exclude
    private Point startPoint;

    @Column(name = "end_point", nullable = false)
    @EqualsAndHashCode.Exclude
    private Point endPoint;


    @Column(name = "created_at", nullable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;

    @Column(name = "ride_accepted_at")
    @EqualsAndHashCode.Exclude
    private LocalDateTime rideAcceptedAt;

    @Column(name = "ride_driver_waiting")
    @EqualsAndHashCode.Exclude
    private LocalDateTime rideDriverWaiting;

    @Column(name = "ride_starttime")
    @EqualsAndHashCode.Exclude
    private LocalDateTime rideStartTime;

    @Column(name = "ride_endtime")
    @EqualsAndHashCode.Exclude
    private LocalDateTime rideEndTime;

    @Column(name = "status", columnDefinition = "ride_status")
    @EqualsAndHashCode.Exclude
    private RideStatus status;


    @Column(name = "ride_actual_price")
    @EqualsAndHashCode.Exclude
    private BigDecimal rideActualPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_rate_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Rate rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promocode_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Promocode promocode;


    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CustomerRating customerRating;


    @OneToMany(mappedBy = "ride", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MatchRequest> matchRequests = new HashSet<>();


    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Payment payment;


    @Override
    public Long getId() {
        return rideId;
    }

    @Override
    public void setId(Long aLong) {
        this.rideId = aLong;
    }
}
