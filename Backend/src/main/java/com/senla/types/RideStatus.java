package com.senla.types;

import lombok.Getter;

@Getter
public enum RideStatus {
    PENDING("pending"),
    CANCELLED("cancelled"),
    ACCEPTED("accepted"),
    WAITING_CLIENT("waiting-client"),
    IN_WAY("in-way"),
    COMPLETED("completed");

    private final String status;

    RideStatus(String status) {
        this.status = status;
    }

    public static RideStatus fromStatus(String status) {
        for (RideStatus statusType : RideStatus.values()) {
            if (statusType.getStatus().equals(status)) {
                return statusType;
            }
        }
        throw new IllegalArgumentException("Invalid status " + status);
    }
}
