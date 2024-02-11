package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Accounting;
import com.example.parabbitmq.data.OrderProduct;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.parabbitmq.RabbitMQConfigurator.*;
//modul roba
@Component
public class ReservationListener {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = RESERVATION_QUEUE)
    public void processReservation(ReservationMessage reservationMessage) {
        //System.out.println("Provera");
        List<Product> productsForReservation = new ArrayList<>();
        ReservationResponse response = new ReservationResponse();
        Accounting accounting = reservationMessage.getAccounting();

        for(OrderProduct orderProduct : reservationMessage.getProductList())
        {
            Product product = orderProduct.getProduct();
            int quantity = product.getQuantity();
            int requestedQuantity = orderProduct.getQuantity();
            if(quantity<requestedQuantity)
            {
                //System.out.println("Nemoguca rezervacija");
                response.setSuccessful(false);
                response.setMessage("Nemoguca rezervacija proizvoda sa id-em " + product.getId());
                //response.setAccounting(accounting);
                rabbitTemplate.convertAndSend(ORDERS2_TOPIC_EXCHANGE_NAME,
                        "reservation.response.queue", response);
                //poslati poruku o neuspesnoj rezervaciji, odnosno kupovini
                return;
            }
            else {
                product.setQuantity(quantity-requestedQuantity);
                productsForReservation.add(product);
                System.out.println("Proizvoda " + product.getProductName() + " ima dovoljno na stanju");

            }

        }

        for(Product product : productsForReservation) {
            productRepository.save(product);
        }
        response.setSuccessful(true);
        response.setMessage("Uspesna rezervacija");
        response.setAccounting(accounting);
        rabbitTemplate.convertAndSend(ORDERS2_TOPIC_EXCHANGE_NAME,
                "reservation.response.queue", response);
        //poslati poruku o uspesnoj rezervaciji
    }
}
