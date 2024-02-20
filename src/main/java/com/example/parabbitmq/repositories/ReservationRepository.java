package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT SUM(r.quantity) FROM Reservation r WHERE r.product.id = :productId")
    Optional<Integer> findTotalReservedQuantityByProductId(@Param("productId") Long productId);

    //naci odredjenu rezervaciju u zavisnosti od proizvoda i kolicine
    @Query("SELECT r.id FROM Reservation r WHERE r.product.id = :productId AND r.quantity = :quantity")
    Optional<Long> findReservationId(@Param("productId") long productId, @Param("quantity") int quantity);

    //naci rezervacije koje se odnose na odredjenu narudzbinu

    @Query("SELECT r.id FROM Reservation r WHERE r.order.id = :orderId")
    List<Reservation> findReservationsByOrderId(@Param("orderId") long orderId);
}
