package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountingRepository extends JpaRepository<Accounting,Long> {
    void flush();

    List<Accounting> findAll();

    Accounting save(Accounting entity);

    void deleteById(long aLong);

    void delete(Accounting entity);

    @Query("SELECT a FROM Accounting a WHERE a.date>=:currentDate AND a.state=0")
    List<Accounting> deadlinePassed(@Param("currentDate") LocalDate currentDate);
}
