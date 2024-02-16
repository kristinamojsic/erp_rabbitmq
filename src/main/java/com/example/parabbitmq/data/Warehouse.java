package com.example.parabbitmq.data;

import jakarta.persistence.*;
//tabela u kojoj se cuva  datum, šifra
//dobavljača i listu artikala koji su primljeni u neko skladište (magacin)
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int warehouseId;
    private int supplierId;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleWarehouse product;
    private int quantity;

    public Warehouse(long id, int warehouseId, ArticleWarehouse product, int quantity, int supplierId) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.product = product;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }

    public Warehouse(int warehouseId, ArticleWarehouse product, int quantity,int supplierId) {
        this.warehouseId = warehouseId;
        this.product = product;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }

    public Warehouse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public ArticleWarehouse getProduct() {
        return product;
    }

    public void setProduct(ArticleWarehouse product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}
