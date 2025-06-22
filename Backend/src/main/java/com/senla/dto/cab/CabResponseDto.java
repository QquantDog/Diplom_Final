package com.senla.dto.cab;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CabResponseDto {

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

}


