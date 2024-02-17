package com.example.parabbitmq.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.parabbitmq.RabbitMQConfigurator.SOLDPRODUCTS_SERVICE_QUEUE;

@Component
public class SoldProductsListener {
    @RabbitListener(queues = SOLDPRODUCTS_SERVICE_QUEUE)
    private void processSoldProductsMessage(SoldProductsMessage soldProductsMessage)
    {
        System.out.println("Iz modula roba: " + "prodao se proizvod");
    }
}
