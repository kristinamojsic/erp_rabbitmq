package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountingRepository extends JpaRepository<Accounting,Long> {
    void flush();

    List<Accounting> findAll();

    Accounting save(Accounting entity);

    void deleteById(long aLong);

    void delete(Accounting entity);
}
