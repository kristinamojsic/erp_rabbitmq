package com.example.parabbitmq.messaging;

import java.io.Serializable;
public class SoldProductsMessage implements Serializable {
    private long orderId;

    public SoldProductsMessage(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public SoldProductsMessage() {
    }
}
