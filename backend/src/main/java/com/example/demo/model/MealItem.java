package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meal_items")
@NoArgsConstructor
@Getter
@Setter
public class MealItem {

    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    private String id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    private Product product;

    private Float quantity;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    public MealItem(Meal meal, Product product, Float quantity) {
        this.meal = meal;
        this.product = product;
        this.quantity = quantity;
    }

    public String getName() {
        return product.getName();
    }

    public Float getCalories() {
        return total(product.getNutrition().getCalories());
    }

    public Float getProteins() {
        return total(product.getNutrition().getProteins());
    }

    public Float getCarbohydrates() {
        return total(product.getNutrition().getCarbohydrates());
    }

    public Float getLipids() {
        return total(product.getNutrition().getLipids());
    }

    private Float total(Float nutrient) {
        return (getQuantity() * nutrient) / 100;
    }
}
