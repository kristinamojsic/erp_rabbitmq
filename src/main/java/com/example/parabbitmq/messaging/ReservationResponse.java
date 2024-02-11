package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Accounting;

import java.io.Serializable;

public class ReservationResponse implements Serializable {
    private String message;
    private boolean successful;
    private transient Accounting accounting;
    public ReservationResponse() {
    }

    public ReservationResponse(String message, boolean successful, Accounting accounting) {
        this.message = message;
        this.successful = successful;
        this.accounting = accounting;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public void setAccounting(Accounting accounting) {
        this.accounting = accounting;
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
