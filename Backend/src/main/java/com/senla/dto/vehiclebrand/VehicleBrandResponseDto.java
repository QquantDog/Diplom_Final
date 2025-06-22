package com.senla.dto.vehiclebrand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBrandResponseDto {
    @JsonProperty("brand_name")
    private String brandName;
}
