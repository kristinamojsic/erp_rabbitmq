package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.OrderProduct;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.parabbitmq.RabbitMQConfigurator.RESERVATION_QUEUE;

@Component
public class ReservationListener {
    @Autowired
    ProductRepository productRepository;
    @RabbitListener(queues = RESERVATION_QUEUE)
    public void processReservation(ReservationMessage reservationMessage) {
        //System.out.println("Provera");
        for(OrderProduct orderProduct : reservationMessage.getProductList())
        {
            Product product = orderProduct.getProduct();
            int quantity = product.getQuantity();
            int requestedQuantity = orderProduct.getQuantity();
            System.out.println("quantity " + quantity + "requested quantity " + requestedQuantity);
            if(quantity<requestedQuantity)
            {
                System.out.println("Nemoguca rezervacija");
                //napisati poruku da je nemoguce rezervisati i prekinuti rezervaciju svih proizvoda iz liste
                //kada modul prodaja primi ovu poruku otkazuje se predracun
                return;
            }
            product.setQuantity(quantity-requestedQuantity);
            productRepository.save(product);
            System.out.println("Uspesna rezervacija proizvoda " + product.getProductName());
        }
        //ako uspe rezervacija svih proizvoda, salje se poruka o uspesnoj rezervaciji
        //kada primi tu poruku, pravi fakturu...

    }
}
