package com.senla.dto.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RidePromocodeEnterDto {
    @NotNull
    @JsonProperty("id")
    private Long rideId;

    @NotNull
    @JsonProperty("promocode_enter_code")
    private String promocodeEnterCode;
}
