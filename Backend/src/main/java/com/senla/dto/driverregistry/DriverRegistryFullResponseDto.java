package com.senla.dto.driverregistry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.driver.DriverResponseDto;
import com.senla.dto.taxicompany.TaxiCompanyResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRegistryFullResponseDto {

    private Long id;

    @JsonProperty("registration_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;

    @JsonProperty("registration_expiration_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationExpirationDate;

    @JsonProperty("driver_info")
    private DriverResponseDto driver;

    @JsonProperty("company_info")
    private TaxiCompanyResponseDto taxiCompany;
}
