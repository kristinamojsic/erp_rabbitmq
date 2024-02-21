package com.example.parabbitmq.roba.listeners;

import com.example.parabbitmq.messaging.ReservationMessage;
import com.example.parabbitmq.messaging.ReservationResponse;
import com.example.parabbitmq.prodaja.data.Accounting;
import com.example.parabbitmq.prodaja.data.OrderProduct;
import com.example.parabbitmq.roba.data.Product;
import com.example.parabbitmq.roba.data.Reservation;
import com.example.parabbitmq.roba.repositories.ReservationRepository;
import com.example.parabbitmq.roba.repositories.WarehouseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.parabbitmq.RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME;
import static com.example.parabbitmq.RabbitMQConfigurator.RESERVATION_QUEUE;

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

        ReservationResponse response = new ReservationResponse();
        Accounting accounting = reservationMessage.getAccounting();
        boolean successful = true;
        for(OrderProduct orderProduct : reservationMessage.getProductList())
        {
            Product product = orderProduct.getProduct();
            Optional<Integer> quantity = warehouseRepository.findTotalQuantityByProductId(product.getId());
            Optional<Integer> reservedQuantity = reservationRepository.findTotalReservedQuantityByProductId(product.getId());
            int totalQauntity = reservedQuantity.isPresent() ? quantity.get() - reservedQuantity.get() : quantity.get();
            int requestedQuantity = orderProduct.getQuantity();
            if(totalQauntity<requestedQuantity)
            {
                successful = false;
                response.setSuccessful(false);
                response.setMessage("Nemoguca rezervacija proizvoda sa id-em " + product.getId());
                rabbitTemplate.convertAndSend(PRODUCTS_TOPIC_EXCHANGE_NAME,
                        "reservation.response.queue", response);
            }
        }
        if(successful)
        {
            for(OrderProduct product : reservationMessage.getProductList()) {
                Reservation reservation = new Reservation(product.getProduct(),product.getQuantity(),accounting.getOrder());
                reservationRepository.save(reservation);
            }
            response.setSuccessful(true);
            response.setMessage("Uspesna rezervacija");
            response.setAccounting(accounting);
            rabbitTemplate.convertAndSend(PRODUCTS_TOPIC_EXCHANGE_NAME,
                    "reservation.response.queue", response);
        }
    }
}
