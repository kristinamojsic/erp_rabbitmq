package com.example.parabbitmq.data;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Accounting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    //java.util
    private LocalDate date;
    private double totalPrice;
    //potvrda?
    private short state = 0;

    public Accounting(Order order, LocalDate date,double totalPrice) {
        this.order = order;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public Accounting(Order order, LocalDate date, short state) {
        this.order = order;
        this.date = date;
        this.state = state;
    }

    public Accounting() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String toString() {
        return "Accounting{" +
                "id=" + id +
                ", order=" + order +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", state=" + state +
                '}';
    }
}
