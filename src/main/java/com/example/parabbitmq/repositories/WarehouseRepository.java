package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    void flush();

    Warehouse save(Warehouse entity);

    Optional<Warehouse> findById(Long aLong);

    boolean existsById(Long aLong);

    void delete(Warehouse entity);

    @Query("SELECT SUM(w.quantity) FROM Warehouse w WHERE w.product.id = :productId")
    Optional<Integer> findTotalQuantityByProductId(@Param("productId") Long productId);

    @Query("SELECT w FROM  Warehouse w WHERE w.product.product.id= :productId")
    List<Warehouse> findPurchacePriceForProductId(@Param("productId") Long productId);

    @Query("SELECT w.warehouseId, SUM(w.quantity) FROM Warehouse w WHERE w.product.id = :productId GROUP BY w.warehouseId")
    List<Object[]> findQuantityForProductIdGroupByWarehouse(@Param("productId") Long productId);


}
