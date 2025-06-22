package com.senla.dto.taxicompany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.cab.CabListWOCompanyResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiCompanyWithCabsResponseDto {

    @JsonProperty("company_id")
    private Long companyId;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("park_code")
    private String parkCode;

    @JsonProperty("cabs")
    private List<CabListWOCompanyResponseDto> cabsResponseList = new ArrayList<>();
}
