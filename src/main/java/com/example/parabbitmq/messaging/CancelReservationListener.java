package com.example.parabbitmq.messaging;

import com.example.parabbitmq.data.Reservation;
import com.example.parabbitmq.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CancelReservationListener {

    @Autowired
    ReservationRepository reservationRepository;

    public void cancelReservation(ReservationCancellation reservationCancellation) {

        System.out.println("Iz modula roba: ponistavanje rezervacija za narudzbinu " + reservationCancellation.getOrderId());

        List<Reservation> reservationList = reservationRepository.findReservationsByOrderId(reservationCancellation.getOrderId());
        if(!reservationList.isEmpty()){
            for(Reservation reservation : reservationList){
                reservationRepository.delete(reservation);
            }
        }
    }
}
