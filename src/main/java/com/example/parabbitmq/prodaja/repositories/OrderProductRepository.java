package com.example.parabbitmq.prodaja.repositories;

import com.example.parabbitmq.prodaja.data.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
    void flush();

    List<OrderProduct> findAll();

    OrderProduct save(OrderProduct entity);

    @Query("SELECT op FROM OrderProduct op WHERE op.order.id=:orderId")
    List<OrderProduct> findOrderProducts(@Param ("orderId") long orderId);
}
