package com.example.demo.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meals")
@NoArgsConstructor
@Getter
public class Meal {

    @Id
    @NotNull
    private String id;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealItem> mealItems;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    public Float getCalories() {
        return (float) mealItems.stream()
                .mapToDouble(
                        mealItem -> total(mealItem, mealItem.getProduct().getNutrition().getCalories()))
                .sum();
    }

    public Float getProteins() {
        return (float) mealItems.stream()
                .mapToDouble(
                        mealItem -> total(mealItem, mealItem.getProduct().getNutrition().getProteins()))
                .sum();
    }

    public Float getCarbohydrates() {
        return (float) mealItems.stream()
                .mapToDouble(
                        mealItem -> total(mealItem, mealItem.getProduct().getNutrition().getCarbohydrates()))
                .sum();
    }

    public Float getLipids() {
        return (float) mealItems.stream()
                .mapToDouble(
                        mealItem -> total(mealItem, mealItem.getProduct().getNutrition().getLipids()) )
                .sum();
    }

    private Float total(MealItem mealItem, Float nutrient){
        return (mealItem.getQuantity() * nutrient) / 100;
    }
}
