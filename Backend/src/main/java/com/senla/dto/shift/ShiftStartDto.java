package com.senla.dto.shift;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftStartDto {

    @JsonProperty("city_id")
    private Long cityId;

    @JsonProperty("cab_id")
    private Long cabId;

    @JsonProperty(value = "shift_start_point")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point shiftStartPoint;
}
