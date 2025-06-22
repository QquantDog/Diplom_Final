package com.senla.dto.shift;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.cab.CabFullResponseDto;
import com.senla.dto.cab.CabResponseDto;
import com.senla.dto.city.CityResponseDto;
import com.senla.dto.rate.RateFullResponseDto;
import com.senla.dto.rate.RateResponseDto;
import com.senla.dto.tier.TierResponseDto;
import com.senla.dto.vehicle.VehicleFullResponseDto;
import com.senla.dto.vehicle.VehicleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftFullResponseDto {

    @JsonProperty("shift_id")
    private Long shiftId;

    @JsonProperty("start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime starttime;

    @JsonProperty("end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endtime;

    @JsonProperty("rate_info")
    private RateFullResponseDto rate;

    @JsonProperty("cab_info")
    private CabFullResponseDto cab;
}
