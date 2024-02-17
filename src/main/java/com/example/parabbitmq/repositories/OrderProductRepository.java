package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.OrderProduct;
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

    //naci sve proizvode jedne porudzbine
    @Query("SELECT op FROM OrderProduct op WHERE op.order.id=:orderId")
    List<OrderProduct> findOrderProducts(@Param ("orderId") long orderId);
}
