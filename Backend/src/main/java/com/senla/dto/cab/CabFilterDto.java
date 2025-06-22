package com.senla.dto.cab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabFilterDto {

    private Boolean isOnShift;
    private String vin;

    private LocalDate manufactureDateMin;
    private LocalDate manufactureDateMax;

    private String licensePlate;
    private String colorDescription;


}
