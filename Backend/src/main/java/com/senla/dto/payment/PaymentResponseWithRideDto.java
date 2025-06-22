package com.senla.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.ride.RideResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseWithRideDto {

    @JsonProperty("overall_price")
    private String overallPrice;

    @JsonProperty("method")
    private String method;

    @JsonProperty("ride_info")
    private RideResponseDto ride;
}
