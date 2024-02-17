package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Invoice;

import java.io.Serializable;

public class SoldProductsMessage implements Serializable {
    private Invoice invoice;

    public SoldProductsMessage(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
