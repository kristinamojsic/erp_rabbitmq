package com.example.parabbitmq.roba.data;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private String measureUnit;

    public Product() {
    }

    public Product(String productName, String measureUnit) {
        this.productName = productName;
        this.measureUnit = measureUnit;
    }


    public Product(long id, String productName, String measureUnit) {
        this.id = id;
        this.productName = productName;
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



    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", measureUnit='" + measureUnit + '\'' +
                '}';
    }
}
