package com.senla.dto.rate;


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
public class RateFilterDto {

    @Positive
    private BigDecimal initPriceMin;
    @Positive
    private BigDecimal initPriceMax;

    @Positive
    private BigDecimal ratePerKmMin;
    @Positive
    private BigDecimal ratePerKmMax;

    @Positive
    private BigDecimal paidWaitingPerMinuteMin;
    @Positive
    private BigDecimal paidWaitingPerMinuteMax;

    @Positive
    private Integer freeTimeInSecondsMin;
    @Positive
    private Integer freeTimeInSecondsMax;
}
