package com.senla.dto.taxicompany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxiCompanyFilterDto {
    private String name;
    private String telephone;
    private String parkCode;
}
