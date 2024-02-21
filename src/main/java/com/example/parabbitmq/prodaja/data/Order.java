package com.example.parabbitmq.prodaja.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerId;
    private String customerName;
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> productList;

    public Order() {
    }

    public Order(long customerId, String customerName, List<OrderProduct> productList) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.productList = productList;
    }

    public Order(long id, long customerId, String customerName, List<OrderProduct> productList) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productList = productList;
    }

    public Order(long id, long customerId, String customerName) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProduct> productList) {
        this.productList = productList;
    }

    public Order addProduct(OrderProduct orderProduct)
    {
        this.productList.add(orderProduct);
        return this;
    }
}
