package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByEan(String ean);

    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);
}
