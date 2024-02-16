package com.example.parabbitmq.data;

import jakarta.persistence.*;


@Entity
@Table(name = "articleWarehouse")
public class ArticleWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    private double purchasePrice;

    public ArticleWarehouse() {
    }

    public ArticleWarehouse(Product product, double purchasePrices) {
        this.product = product;
        this.purchasePrice = purchasePrices;
    }

    public ArticleWarehouse(Long id, Product product, double purchasePrice) {
        this.id = id;
        this.product = product;
        this.purchasePrice = purchasePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
