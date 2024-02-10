package com.example.parabbitmq.messaging;


//poruka koju salje modul order modulu roba

import com.example.parabbitmq.data.OrderProduct;

import java.io.Serializable;
import java.util.List;

public class ReservationMessage implements Serializable {

    private List<OrderProduct> productList;

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
