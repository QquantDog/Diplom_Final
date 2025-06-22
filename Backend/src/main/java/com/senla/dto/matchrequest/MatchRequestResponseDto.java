package com.senla.dto.matchrequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.ride.RideResponseDto;
import com.senla.dto.shift.ShiftResponseDto;
import com.senla.types.MatchRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchRequestResponseDto {

    @JsonProperty("init_distance")
    private BigDecimal initDistance;

    @JsonProperty("match_request_status")
    private MatchRequestStatus matchRequestStatus;

    @JsonProperty("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("ride_info")
    private RideResponseDto ride;

    @JsonProperty("shift_info")
    private ShiftResponseDto shift;
}
