package com.senla.model.matchrequest;

import com.senla.model.ride.Ride;
import com.senla.model.shift.Shift;
import com.senla.types.MatchRequestStatus;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "match_requests")
public class MatchRequest implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;


    @Column(name = "init_distance", nullable = false)
    @EqualsAndHashCode.Exclude
    private BigDecimal initDistance;

    @Column(name = "match_request_status", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @EqualsAndHashCode.Exclude
    private MatchRequestStatus matchRequestStatus;


    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ride_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shift_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Shift shift;



    @Override
    @Transient
    public Long getId() {
        return matchId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.matchId = aLong;
    }
}