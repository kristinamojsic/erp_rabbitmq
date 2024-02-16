package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.ArticleWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ArticleWarehouseRepository extends JpaRepository<ArticleWarehouse,Long> {
    void flush();

    ArticleWarehouse saveAndFlush(ArticleWarehouse entity);

    //List<ArticleWarehouse> saveAll(List<ArticleWarehouse> entities);

    List<ArticleWarehouse> findAll();

    ArticleWarehouse save(ArticleWarehouse entity);

    Optional<ArticleWarehouse> findById(Long aLong);
}
