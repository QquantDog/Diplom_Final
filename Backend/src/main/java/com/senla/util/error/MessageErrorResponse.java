package com.senla.util.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageErrorResponse extends CustomErrorResponse {
    private String message;

    public MessageErrorResponse(int status, String message, long milliseconds) {
        super(status, milliseconds);
        this.message = message;
    }
}
