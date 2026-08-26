package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByEan(String ean);

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE (:name = '' OR :name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Product> getProducts(@Param("name") String name, Pageable pageable);
}
