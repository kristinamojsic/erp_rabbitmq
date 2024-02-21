package com.example.parabbitmq.messaging;

import com.example.parabbitmq.roba.data.Product;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.List;

public class ProductEvent implements Serializable {
    public static enum EventType { NONE, NEW_PRODUCT, UPDATE_STATE_PRODUCT, UPDATE_PRICE_PRODUCT};

    EventType eventType = EventType.NONE;
    List<Product> productList;
    Product product;

    public ProductEvent() {
    }
    @JsonCreator
    public ProductEvent(EventType eventType,List<Product> products) {
        this.eventType = eventType;
        this.productList = products;
    }

    public ProductEvent(EventType eventType, Product product) {
        this.eventType = eventType;
        this.product = product;
    }

    public EventType getEventType() {
        return eventType;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static ProductEvent createNewProduct(Product newProduct){
            ProductEvent productEvent = new ProductEvent(EventType.NEW_PRODUCT,newProduct);
            return  productEvent;
        }
    public static ProductEvent updateStateOfProduct(List<Product> updatedProducts){
        ProductEvent productEvent = new ProductEvent(EventType.UPDATE_STATE_PRODUCT,updatedProducts);
        return  productEvent;
    }
    /*public static ProductEvent updatePriceOfProduct(Product updatedProduct){
        ProductEvent productEvent = new ProductEvent(EventType.UPDATE_PRICE_PRODUCT, updatedProduct);
        return  productEvent;
    }*/

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductEvent{").append("eventType=").append(eventType).append("Product:\n");
        if(productList.size()!=0) {
            productList.forEach(p -> sb.append(p).append(" "));
        }

        if(product!=null) sb.append(product);
        return sb.toString();
    }
}
