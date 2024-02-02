package com.example.parabbitmq.data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "accounting_id")
    private Accounting accounting;
    private LocalDate payDate;

    public Invoice() {
    }

    public Invoice(Accounting accounting, LocalDate payDate) {
        this.accounting = accounting;
        this.payDate = payDate;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public void setAccounting(Accounting accounting) {
        this.accounting = accounting;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }
}
