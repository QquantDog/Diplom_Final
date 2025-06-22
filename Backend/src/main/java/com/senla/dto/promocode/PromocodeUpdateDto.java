package com.senla.dto.promocode;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PromocodeUpdateDto {

    @JsonProperty("code")
    @NotBlank(message = "Promocode name must be present")
    private String promocodeCode;

    @JsonProperty("discount")
    @Min(value = 0, message = "Discount value must pe positive")
    @Max(value = 1, message = "Discount value couldn't be more than 100 percent")
    @NotNull
    private BigDecimal discountValue;

    @JsonProperty("start_date")
    @NotNull
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull
    private LocalDate endDate;

    private String description;
}