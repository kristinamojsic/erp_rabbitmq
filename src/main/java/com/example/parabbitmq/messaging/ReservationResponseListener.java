package com.example.parabbitmq.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.parabbitmq.RabbitMQConfigurator.RESERVATION_RESPONSE_QUEUE;

@Component
public class ReservationResponseListener {
    @RabbitListener(queues = RESERVATION_RESPONSE_QUEUE)
    public void processReservationResponse(ReservationResponse reservationResponse)
    {
        //proba
        System.out.println(reservationResponse.getMessage());
        //ispitivanje poruke, ako je uspesna rezervacija, napraviti fakturu, ako je neuspesna otkazati predracun
    }
}
