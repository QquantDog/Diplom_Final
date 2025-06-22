package com.senla.dto.cab;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.vehicle.VehicleFullResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabListWOCompanyResponseDto {

    @JsonProperty("cab_id")
    private Long cabId;

    @NotNull
    @JsonProperty("vin")
    private String vin;

    @NotNull
    @JsonProperty("manufacture_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufactureDate;

    @NotNull
    @JsonProperty("color")
    private String colorDescription;

    @NotNull
    @JsonProperty("license_plate")
    private String licensePlate;

    @JsonProperty("vehicle_info")
    private VehicleFullResponseDto vehicle;

}