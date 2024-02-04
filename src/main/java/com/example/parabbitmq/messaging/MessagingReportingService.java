package com.example.parabbitmq.messaging;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MessagingReportingService implements Serializable {
    public void receiveMessage(ProductEvent event) {
        System.out.println("New event: <" + event.getEventType() + "> on proizvod with id = "+event.getProduct().getId());
    }
}
