package com.senla.dto.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideTipDto {
    @NotNull
    @JsonProperty("id")
    private Long rideId;

    @NotNull
    @JsonProperty("tip_amount")
    private BigDecimal tipAmount;
}
