package com.senla.converter;

import com.senla.types.RideStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RideStatusConverter implements AttributeConverter<RideStatus, String> {
    @Override
    public String convertToDatabaseColumn(RideStatus rideStatus) {
        if(rideStatus == null) {
            return null;
        }
        return rideStatus.getStatus();
    }

    @Override
    public RideStatus convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        return RideStatus.fromStatus(s);
    }
}
