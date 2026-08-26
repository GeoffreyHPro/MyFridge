package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MealItem;

public interface MealItemRepository extends JpaRepository<MealItem, String>{
    
    List<MealItem> findByProductId(String productId);
}
