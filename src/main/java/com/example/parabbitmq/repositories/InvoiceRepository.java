package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findAll();

    Invoice save(Invoice entity);

    Optional<Invoice> findById(Long aLong);

    void deleteById(long aLong);

    void delete(Invoice entity);
}
