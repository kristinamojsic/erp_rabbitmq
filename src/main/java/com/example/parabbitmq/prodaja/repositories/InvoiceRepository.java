package com.example.parabbitmq.prodaja.repositories;

import com.example.parabbitmq.prodaja.data.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findAll();

    Invoice save(Invoice entity);

    Optional<Invoice> findById(Long aLong);

    void deleteById(long aLong);

    void delete(Invoice entity);
}
