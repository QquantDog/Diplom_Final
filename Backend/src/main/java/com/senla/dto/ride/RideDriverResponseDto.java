package com.senla.dto.ride;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senla.dto.payment.PaymentResponseDto;
import com.senla.dto.promocode.PromocodeResponseDto;
import com.senla.dto.rate.RateResponseDto;
import com.senla.dto.shift.ShiftResponseIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDriverResponseDto {

    @NotNull
    @JsonProperty("id")
    private Long rideId;

    @JsonProperty("ride_tip")
    private BigDecimal rideTip;


    @JsonProperty("start_point")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point startPoint;


    @JsonProperty("end_point")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point endPoint;


    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("accepted_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rideAcceptedAt;

    @JsonProperty("driver_waiting")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rideDriverWaiting;

    @JsonProperty("start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rideStartTime;

    @JsonProperty("end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rideEndTime;

    private String status;

    @JsonProperty("expected_price")
    private BigDecimal rideExpectedPrice;

    @JsonProperty("actual_price")
    private BigDecimal rideActualPrice;

    @JsonProperty("distance_expected_meters")
    private BigDecimal rideDistanceExpectedMeters;

    @JsonProperty("distance_actual_meters")
    private BigDecimal rideDistanceActualMeters;

    @JsonProperty("accepted_rate_info")
    private RateResponseDto rate;

    @JsonProperty("promocode_info")
    private PromocodeResponseDto promocode;

    @JsonProperty("payment_info")
    private PaymentResponseDto payment;

    @JsonProperty("shift_info")
    private ShiftResponseIdDto shift;

}
