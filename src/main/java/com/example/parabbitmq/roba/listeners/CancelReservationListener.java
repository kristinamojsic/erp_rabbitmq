package com.example.parabbitmq.roba.listeners;

import com.example.parabbitmq.messaging.ReservationCancellation;
import com.example.parabbitmq.roba.data.Reservation;
import com.example.parabbitmq.roba.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CancelReservationListener {

    @Autowired
    ReservationRepository reservationRepository;

    public void cancelReservation(ReservationCancellation reservationCancellation) {

        System.out.println("Modul <ROBA> dobija poruku o ponistavanju rezervacije za narudzbinu " + reservationCancellation.getOrderId());

        List<Reservation> reservationList = reservationRepository.findReservationsByOrderId(reservationCancellation.getOrderId());
        if(!reservationList.isEmpty()){
            for(Reservation reservation : reservationList){
                reservationRepository.delete(reservation);
            }
        }
    }
}
