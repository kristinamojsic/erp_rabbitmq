package com.example.parabbitmq.messaging;

import java.io.Serializable;

public class ReservationCancellation implements Serializable {
    private long orderId;

    public ReservationCancellation() {
    }

    public ReservationCancellation(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
