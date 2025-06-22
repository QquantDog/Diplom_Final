package com.senla.dto.vehiclebrand;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBrandUpdateDto {
    private Long brandId;
    private String brandName;
}
