package com.example.parabbitmq.data;

import jakarta.persistence.*;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private double purchasePrice;
    private String measureUnit;
    private int quantity = 0;

    public Product() {
    }

    public Product(String productName, double purchasePrice, String measureUnit) {
        this.productName = productName;
        this.purchasePrice = purchasePrice;
        this.measureUnit = measureUnit;
    }

    public Product(String productName, double purchasePrice, String measureUnit, int quantity) {
        this.productName = productName;
        this.purchasePrice = purchasePrice;
        this.measureUnit = measureUnit;
        this.quantity = quantity;
    }

    public Product(long id, String productName, double purchasePrice, String measureUnit) {
        this.id = id;
        this.productName = productName;
        this.purchasePrice = purchasePrice;
        this.measureUnit = measureUnit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void updateState(Product product)
    {
        this.productName = product.productName;
        this.purchasePrice = product.purchasePrice;
        this.measureUnit = product.measureUnit;
        this.quantity = product.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", measureUnit='" + measureUnit + '\'' +
                '}';
    }
}
