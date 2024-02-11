package com.example.parabbitmq.messaging;

import java.io.Serializable;

public class ReservationResponse implements Serializable {
    private String message;
    private boolean successful;

    public ReservationResponse() {
    }

    public ReservationResponse(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
