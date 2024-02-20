package com.example.parabbitmq.messaging;

import org.springframework.stereotype.Component;

@Component
public class CancelReservationListener {
    public void cancelReservation(ReservationCancellation reservationCancellation) {
        System.out.println("Iz modula roba: ponistavanje rezervacija za narudzbinu " + reservationCancellation.getOrderId());
    }
}
