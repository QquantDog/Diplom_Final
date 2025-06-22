package com.senla.dto.tier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierUpdateDto {
    @JsonProperty("tier_name")
    private String tierName;

    @JsonProperty("description")
    private String description;
}