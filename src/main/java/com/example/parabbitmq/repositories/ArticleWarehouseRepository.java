package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.ArticleWarehouse;
import com.example.parabbitmq.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT aw from ArticleWarehouse aw WHERE aw.product=:product AND aw.purchasePrice=:purchasePrice")
    Optional<ArticleWarehouse> findArticleWarehouse(@Param("product") Product product,@Param("purchasePrice") double purchasePrice);
}
