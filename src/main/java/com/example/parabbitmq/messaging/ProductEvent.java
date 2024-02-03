package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Product;

import java.io.Serializable;

public class ProductEvent implements Serializable {
    public static enum EventType { NONE, NEW_PRODUCT, UPDATE_STATE_PRODUCT, UPDATE_PRICE_PRODUCT};
    EventType eventType = EventType.NONE;
    Product product = null;

    public ProductEvent(EventType eventType, Product product) {
        this.eventType = eventType;
        this.product = product;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Product getProduct() {
        return product;
    }

    public static ProductEvent createNewProduct(Product newProduct){
        ProductEvent productEvent = new ProductEvent(EventType.NEW_PRODUCT,newProduct);
        return  productEvent;
    }
    public static ProductEvent updateStateOfProduct(Product updatedProduct){
        ProductEvent productEvent = new ProductEvent(EventType.UPDATE_STATE_PRODUCT,updatedProduct);
        return  productEvent;
    }
    public static ProductEvent updatePriceOfProduct(Product updatedProduct){
        ProductEvent productEvent = new ProductEvent(EventType.UPDATE_PRICE_PRODUCT, updatedProduct);
        return  productEvent;
    }
}
