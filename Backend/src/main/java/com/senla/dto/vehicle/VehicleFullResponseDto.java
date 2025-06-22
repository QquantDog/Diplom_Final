package com.senla.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.vehiclebrand.VehicleBrandResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleFullResponseDto {

    @NotNull
    @JsonProperty("id")
    private Long vehicleId;

    @NotNull
    @JsonProperty("vehicle_model")
    private String vehicleModel;

    @NotNull
    @JsonProperty("seats_number")
    private String seatsNumber;

    @NotNull
    @JsonProperty("brand")
    private VehicleBrandResponseDto brand;
}
