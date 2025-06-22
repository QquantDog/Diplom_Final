package com.senla.dto.pagination;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationDetails {
    @NotNull
    @Positive
    private Integer offset;

    @NotNull
    @Positive
    private Integer limit;
}