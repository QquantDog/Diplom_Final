package com.senla.dto.rate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateUpdateDto {

    @NotNull
    @Positive
    @JsonProperty("init_price")
    private BigDecimal initPrice;

    @NotNull
    @Positive
    @JsonProperty("rate_per_km")
    private BigDecimal ratePerKm;

    @NotNull
    @Positive
    @JsonProperty("paid_waiting_per_minute")
    private BigDecimal paidWaitingPerMinute;

    @NotNull
    @Positive
    @JsonProperty("free_time")
    private Integer freeTimeInSeconds;

    @NotNull
    @JsonProperty("city_id")
    private Long cityId;

    @NotNull
    @JsonProperty("tier_id")
    private Long tierId;
}
