package com.example.parabbitmq.prodaja.data;

import com.example.parabbitmq.roba.data.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class OrderProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    private double pricePerUnit;
    private double pdv;
    private double totalPrice;
    private int quantity;

    public OrderProduct(long id, Product product, double pricePerUnit, double pdv, int quantity, double totalPrice) {
        this.id = id;
        this.product = product;
        this.pricePerUnit = pricePerUnit;
        this.pdv = pdv;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public OrderProduct(Order order,Product product, double pdv, int quantity) {
        this.order = order;
        this.product = product;
        this.pdv = pdv;
        this.quantity = quantity;
        this.pricePerUnit = 35.5;
        this.totalPrice = (pricePerUnit + pdv) * quantity;
    }

    public OrderProduct() {
    }

    public OrderProduct(Product product, double pricePerUnit, double pdv, int quantity) {
        this.product = product;
        this.pricePerUnit = pricePerUnit;
        this.pdv = pdv;
        this.quantity = quantity;
        this.totalPrice = quantity * (pricePerUnit + pdv);
    }

    public OrderProduct(Product product, double pdv, double pricePerUnit, int quantity, double totalPrice) {
        this.product = product;
        this.pricePerUnit = pricePerUnit;
        this.pdv = pdv;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
