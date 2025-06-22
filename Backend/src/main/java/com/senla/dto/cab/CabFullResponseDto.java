package com.senla.dto.cab;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.taxicompany.TaxiCompanyResponseDto;
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
public class CabFullResponseDto {

    @JsonProperty("cab_id")
    private Long cabId;

    @NotNull
    @JsonProperty("vin")
    private String vin;

    @NotNull
    @JsonProperty("is_on_shift")
    private Boolean isOnShift;

    @NotNull
    @JsonProperty("manufacture_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate manufactureDate;

    @NotNull
    @JsonProperty("color")
    private String colorDescription;

    @NotNull
    @JsonProperty("license_plate")
    private String licensePlate;

    @JsonProperty("vehicle_info")
    private VehicleFullResponseDto vehicle;

    @JsonProperty("company_info")
    private TaxiCompanyResponseDto company;
}
