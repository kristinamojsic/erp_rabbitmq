package com.example.parabbitmq;

import com.example.parabbitmq.messaging.MessagingReportingService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final MessagingReportingService receiver;

    public Runner(MessagingReportingService receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("Sending message...");
        //rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.TOPIC_EXCHANGE_NAME, "foo.bar.baz", "Hello from RabbitMQ!");
        //receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
