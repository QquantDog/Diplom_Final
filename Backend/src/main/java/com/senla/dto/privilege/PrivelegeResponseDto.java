package com.senla.dto.privilege;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivelegeResponseDto {

    @JsonProperty("privilege_code")
    private String privilegeCode;

    @JsonProperty("privilege_name")
    private String privilegeName;

    @JsonProperty()
    private String description;
}
