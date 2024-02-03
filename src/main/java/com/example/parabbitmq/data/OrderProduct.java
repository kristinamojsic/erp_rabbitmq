package com.example.parabbitmq.data;

import jakarta.persistence.*;
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private double pricePerUnit;
    private double pdv;
    private int quantity;
    private double totalPrice;

    public OrderProduct(long id, Product product, double pricePerUnit, double pdv, int quantity, double totalPrice) {
        this.id = id;
        this.product = product;
        this.pricePerUnit = pricePerUnit;
        this.pdv = pdv;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public OrderProduct() {
    }

    public OrderProduct(Product product, double pricePerUnit, double pdv, int quantity, double totalPrice) {
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
}