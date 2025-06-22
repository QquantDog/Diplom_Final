package com.senla.util.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {
    private int status;
    private long timestamp;

    public CustomErrorResponse(int status, long milliseconds) {
        this.status = status;
        this.timestamp = milliseconds;
    }

}
