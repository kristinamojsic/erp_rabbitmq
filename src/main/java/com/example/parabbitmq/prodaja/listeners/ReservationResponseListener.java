package com.example.parabbitmq.prodaja.listeners;

import com.example.parabbitmq.messaging.ReservationResponse;
import com.example.parabbitmq.prodaja.repositories.AccountingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationResponseListener {
    @Autowired
    AccountingRepository accountingRepository;
    //@RabbitListener(queues = RESERVATION_RESPONSE_QUEUE)
    public void processReservationResponse(ReservationResponse reservationResponse)
    {
        if(reservationResponse.isSuccessful()) {
            accountingRepository.save(reservationResponse.getAccounting());
            System.out.println(reservationResponse.getMessage());
        }
        else {
            System.out.println(reservationResponse.getMessage());
        }
    }
}
