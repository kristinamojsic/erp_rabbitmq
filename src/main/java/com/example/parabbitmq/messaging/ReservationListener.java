package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.OrderProduct;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.parabbitmq.RabbitMQConfigurator.RESERVATION_QUEUE;

@Component
public class ReservationListener {
    @Autowired
    ProductRepository productRepository;
    @RabbitListener(queues = RESERVATION_QUEUE)
    public void processReservation(ReservationMessage reservationMessage) {
        //System.out.println("Provera");
        List<Product> productsForReservation = new ArrayList<>();
        for(OrderProduct orderProduct : reservationMessage.getProductList())
        {
            Product product = orderProduct.getProduct();
            int quantity = product.getQuantity();
            int requestedQuantity = orderProduct.getQuantity();
            if(quantity<requestedQuantity)
            {
                System.out.println("Nemoguca rezervacija");
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
        //poslati poruku o uspesnoj rezervaciji
    }
}
