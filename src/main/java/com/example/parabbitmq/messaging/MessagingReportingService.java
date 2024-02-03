package com.example.parabbitmq.messaging;

public class MessagingReportingService {
    public void receiveMessage(ProductEvent event) {
        System.out.println("New event: <" + event.getEventType() + "> on proizvod with id = "+event.getProduct().getId());
    }
}
