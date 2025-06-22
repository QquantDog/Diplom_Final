package com.senla.dto.promocode;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromocodeFilterDto {

    private String promocodeCode;

    @Positive
    @Max(1)
    private BigDecimal discountMin;

    @Positive
    @Max(1)
    private BigDecimal discountMax;

    private LocalDate startDateMin;
    private LocalDate startDateMax;

    private LocalDate endDateMin;
    private LocalDate endDateMax;
}
