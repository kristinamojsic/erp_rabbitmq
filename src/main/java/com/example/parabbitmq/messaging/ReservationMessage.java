package com.example.parabbitmq.messaging;


//poruka koju salje modul order modulu roba

import com.example.parabbitmq.prodaja.data.Accounting;
import com.example.parabbitmq.prodaja.data.OrderProduct;

import java.io.Serializable;
import java.util.List;

public class ReservationMessage implements Serializable {

    private List<OrderProduct> productList;
    private Accounting accounting;

    public ReservationMessage(List<OrderProduct> productList, Accounting accounting) {
        this.productList = productList;
        this.accounting = accounting;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public void setAccounting(Accounting accounting) {
        this.accounting = accounting;
    }

    public ReservationMessage(List<OrderProduct> productList) {
        this.productList = productList;
    }

    public List<OrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProduct> productList) {
        this.productList = productList;
    }

    public ReservationMessage() {
    }
}
