package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReservationRepository extends JpaRepository <Reservation,Long>{
    void flush();

    Reservation save(Reservation entity);

    Optional<Reservation> findById(Long aLong);

    void delete(Reservation entity);

    List<Reservation> findAll();

}
