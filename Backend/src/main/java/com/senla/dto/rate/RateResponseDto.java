package com.senla.dto.rate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateResponseDto {

    @JsonProperty("id")
    private Long rateId;

    @JsonProperty("init_price")
    private BigDecimal initPrice;

    @JsonProperty("rate_per_km")
    private BigDecimal ratePerKm;

    @JsonProperty("paid_waiting_per_minute")
    private BigDecimal paidWaitingPerMinute;

    @JsonProperty("free_time")
    private Integer freeTimeInSeconds;
}
