package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.NutritionDay;
import com.example.demo.model.User;

public interface NutritionDayRepository extends JpaRepository<NutritionDay, String>{
    List<NutritionDay> findNutritionDaysByUser(User user);

    Optional<NutritionDay> findNutritionDaysByUserAndDate(User user, LocalDate date);

    Optional<NutritionDay> findNutritionDayByUserAndId(User user, String id);
}
