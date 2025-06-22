package com.senla.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleUpdateDto {

    @NotNull
    @JsonProperty("vehicle_model")
    private String vehicleModel;

    @NotNull
    @JsonProperty("seats_number")
    private String seatsNumber;

    @NotNull
    @JsonProperty("brand_id")
    private Long brandId;

    @NotNull
    @JsonProperty("tier_id")
    private Long tierId;
}
