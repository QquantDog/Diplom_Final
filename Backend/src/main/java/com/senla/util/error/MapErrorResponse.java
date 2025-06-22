package com.senla.util.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MapErrorResponse extends CustomErrorResponse {
    private Map<String, String> errors;

    public MapErrorResponse(int status, Map<String, String> errors, long milliseconds) {
        super(status, milliseconds);
        this.errors = errors;
    }
}
