package com.senla.dto.rate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.city.CityResponseDto;
import com.senla.dto.tier.TierResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateFullResponseDto {

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

    @JsonProperty("city_info")
    private CityResponseDto city;

    @JsonProperty("tier_info")
    private TierResponseDto tier;
}