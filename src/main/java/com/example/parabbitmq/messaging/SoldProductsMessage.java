package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Invoice;

import java.io.Serializable;
//modulu Roba da kreira jedan ili više
//objekata Otpremnica kojim se sa jednog ili više magacina skida količina robe (prethodno rezervisana) sa
//stanja i time daje nalog da je spremna za izdavanje kupcu.
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

    public SoldProductsMessage() {
    }
}
