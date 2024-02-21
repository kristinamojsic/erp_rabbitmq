package com.example.parabbitmq.prodaja.listeners;

import com.example.parabbitmq.messaging.ProductEvent;
import com.example.parabbitmq.roba.data.Product;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.example.parabbitmq.messaging.ProductEvent.EventType.NEW_PRODUCT;

@Component
public class ProductEventReportingService implements Serializable {
    public void receiveMessage(ProductEvent event) {
        System.out.println("Modul <PRODAJA> dobija poruku:");

        if(event.getEventType().equals(NEW_PRODUCT)) {
            System.out.println("New event: <" + event.getEventType() + "> on product with id = "+event.getProduct().getId());
        } else {
            System.out.println("New event: <" + event.getEventType());
            StringBuilder sb = new StringBuilder();
            for(Product product : event.getProducts()) sb.append("on product with id = ").append(product.getId()).append("\n");
            System.out.println(sb);
        }
    }
}
