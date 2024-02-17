package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Accounting;
import com.example.parabbitmq.data.OrderProduct;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.data.Reservation;
import com.example.parabbitmq.repositories.ReservationRepository;
import com.example.parabbitmq.repositories.WarehouseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.parabbitmq.RabbitMQConfigurator.ORDERS2_TOPIC_EXCHANGE_NAME;
import static com.example.parabbitmq.RabbitMQConfigurator.RESERVATION_QUEUE;
//modul roba
@Component
public class ReservationListener {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @RabbitListener(queues = RESERVATION_QUEUE)
    public void processReservation(ReservationMessage reservationMessage) {
        //System.out.println("Provera");
        //List<Product> productsForReservation = new ArrayList<>();
        ReservationResponse response = new ReservationResponse();
        Accounting accounting = reservationMessage.getAccounting();
        boolean successful = true;
        for(OrderProduct orderProduct : reservationMessage.getProductList())
        {
            Product product = orderProduct.getProduct();
            Optional<Integer> quantity = warehouseRepository.findTotalQuantityByProductId(product.getId());
            Optional<Integer> reservedQuantity = reservationRepository.findTotalReservedQuantityByProductId(product.getId());
            int totalQauntity = reservedQuantity.get()!=null ? quantity.get() - reservedQuantity.get() : quantity.get();
            int requestedQuantity = orderProduct.getQuantity();
            if(totalQauntity<requestedQuantity)
            {
                successful = false;
                response.setSuccessful(false);
                response.setMessage("Nemoguca rezervacija proizvoda sa id-em " + product.getId());
                rabbitTemplate.convertAndSend(ORDERS2_TOPIC_EXCHANGE_NAME,
                        "reservation.response.queue", response);
            }
        }
        if(successful)
        {
            for(OrderProduct product : reservationMessage.getProductList()) {
                Reservation reservation = new Reservation(product.getProduct(),product.getQuantity());
                reservationRepository.save(reservation);
            }
            response.setSuccessful(true);
            response.setMessage("Uspesna rezervacija");
            response.setAccounting(accounting);
            rabbitTemplate.convertAndSend(ORDERS2_TOPIC_EXCHANGE_NAME,
                    "reservation.response.queue", response);
        }
    }
}
